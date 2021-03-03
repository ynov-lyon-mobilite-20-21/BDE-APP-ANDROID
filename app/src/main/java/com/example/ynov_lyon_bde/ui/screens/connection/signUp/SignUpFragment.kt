package com.example.ynov_lyon_bde.ui.screens


import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.domain.utils.SpinnerService
import com.example.ynov_lyon_bde.domain.viewmodel.signUp.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_createuser.*
import kotlinx.android.synthetic.main.fragment_createuser.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//TODO les contraintes graohiques et le design du fragment_createuser ne sont pas assez responsives, il faut reprendre le design pour qu'il puisse fonctionner
// avec le plus grand nombre de device et ce qu'il fasse 4" ou 6"
class SignUpFragment: Fragment() {

    //TODO tu as de la logique ici qui devrait être dans un viewModel, fais attention à respecter l'archi MVVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_createuser, container, false)

        val signUpViewModel = SignUpViewModel()
        val spinnerAdapter = SpinnerService()

        val promotion = arrayOf("Classe", "Bachelor 1",
            "Bachelor 2", "Bachelor 3",
            "Mastère 1", "Mastère 2")

        val formation = arrayOf("Filière",
            "Animation 3D",
            "Audiovisuel",
            "Création & Design",
            "Marketing Communication",
            "Informatique")

        view.spinnerFormation.adapter = spinnerAdapter.initAdapter(requireContext(), formation, view.spinnerFormation)
        view.spinnerPromotion.adapter = spinnerAdapter.initAdapter(requireContext(), promotion, view.spinnerPromotion)

        //Show / Hide button
        view.showHideButton.setOnClickListener {
            signUpViewModel.showHideBehaviour(editTextPassword, showHideButton)
        }

        view.buttonCreateUser.setOnClickListener {

            // Take informations User
            val contentEditTextMail = editTextMail.text.toString();
            val mail = if(Patterns.EMAIL_ADDRESS.matcher(contentEditTextMail).matches()) {
                contentEditTextMail
            } else null
            var names: MutableList<String>? = null
            if (mail != null) {
                names = signUpViewModel.checkNames(editTextMail.text.toString()) // firstname + lastname
            }

            val password = editTextPassword.text.toString()
            val promotion = spinnerPromotion.selectedItem.toString()
            val formation = spinnerFormation.selectedItem.toString()

            if (names != null && mail != null && password != "" && signUpViewModel.spinnerInformed(mutableListOf(promotion, formation))) {
                var message: String? = null

                //send request to api to create user
                GlobalScope.launch(Dispatchers.Main) {
                    val deferred = async(Dispatchers.IO) {
                        //call requests
                        message = context?.let { it1 -> signUpViewModel.create(names,mail,password,promotion,formation,it1) }
                    }
                    deferred.await()
                    if (message.isNullOrEmpty()) {
                        activity?.finish()
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Formulaire mal renseigné", Toast.LENGTH_SHORT).show()
            }
        }
        return view

    }
}




