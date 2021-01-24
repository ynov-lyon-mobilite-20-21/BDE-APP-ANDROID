package com.example.ynov_lyon_bde.ui.screens


import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO
import com.example.ynov_lyon_bde.data.model.DTO.UserDTO
import com.example.ynov_lyon_bde.domain.utils.SpinnerService
import com.example.ynov_lyon_bde.domain.viewmodel.SignUpViewModel
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

        val createUserViewModel = SignUpViewModel()
        val spinnerAdapter = SpinnerService()

        val promotion = arrayOf<String>("Classe", "Bachelor 1",
            "Bachelor 2", "Bachelor 3",
            "Mastère 1", "Mastère 2")

        val formation = arrayOf<String>("Filière",
            "Animation 3D",
            "Audiovisuel",
            "Création & Design",
            "Marketing Communication",
            "Informatique")

        view.spinnerFormation.adapter = spinnerAdapter.initAdapter(requireContext(), formation, view.spinnerFormation)
        view.spinnerPromotion.adapter = spinnerAdapter.initAdapter(requireContext(), promotion, view.spinnerPromotion)
        //view.spinnerFormation.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, formation)
        //view.spinnerPromotion.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, promotion)

        //Show / Hide button
        view.showHideButton.setOnClickListener {
            if(editTextPassword.transformationMethod == PasswordTransformationMethod.getInstance()){
                editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                showHideButton.setImageResource(R.drawable.userconnect_illustration_noshow_password)
            } else{
                editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                showHideButton.setImageResource(R.drawable.userconnect_illustration_show_password)
            }
        }

        view.buttonCreateUser.setOnClickListener {

            // Take informations User
            val email =
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(editTextMail.text.toString())
                        .matches()
                ) editTextMail.text.toString() else null
            var firstName: String? = null
            var lastName: String? = null
            if (email != null) {
                val names: String = editTextMail.text.toString().split('@')[0]
                firstName = names.split('.')[0]
                lastName = names.split('.')[1]
            }

            val password = editTextPassword.text.toString()
            val promotion = spinnerPromotion.selectedItem.toString()
            val formation = spinnerFormation.selectedItem.toString()

            if (firstName != null && lastName != null && email != null && password != "" && promotion != "" && formation != "") {
                var message: String? = null

                //create DTO models
                val userDto = UserDTO(
                    firstName = firstName,
                    lastName = lastName,
                    mail = email,
                    password = password,
                    promotion = promotion,
                    formation = formation
                )
                val loginDto = LoginDTO(
                    mail = email,
                    password = password
                )

                //send request to api to create user
                GlobalScope.launch(Dispatchers.Main) {
                    val deferred = async(Dispatchers.IO) {
                        //call requests
                        try {
                            context?.let { it1 ->
                                createUserViewModel.callApi(userDto, loginDto,
                                    it1
                                )
                            }
                        } catch (err: Exception) {
                            message = err.message
                            Log.e("message", message)
                        }
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




