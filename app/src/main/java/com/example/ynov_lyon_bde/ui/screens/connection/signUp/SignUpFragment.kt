package com.example.ynov_lyon_bde.ui.screens.connection.signUp

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.domain.viewmodel.signUp.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_createuser.*
import kotlinx.android.synthetic.main.fragment_createuser.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SignUpFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_createuser, container, false)

        val signUpViewModel = SignUpViewModel()

        val promotion = arrayOf("Bachelor 1",
            "Bachelor 2", "Bachelor 3",
            "Mastère 1", "Mastère 2")

        val formation = arrayOf("Animation 3D",
            "Audiovisuel",
            "Création & Design",
            "Marketing Communication",
            "Informatique")

        val adapterFormation = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, formation)
        val adapterPromotion = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, promotion)

        view.spinnerFormation.setAdapter(adapterFormation)
        view.spinnerPromotion.setAdapter(adapterPromotion)

        view.buttonCreateUserSignUp.setOnClickListener {
            // get text input
            val contentEditTextMail = editTextMailCreate.text.toString()
            val mail = if(Patterns.EMAIL_ADDRESS.matcher(contentEditTextMail).matches()) {
                contentEditTextMail
            } else null

            val firstName = editTextLastName.text.toString()
            val lastName = editTextFirstName.text.toString()
            val password = editTextPasswordCreate.text.toString()
            val promotion = spinnerPromotion.text.toString()
            val formation = spinnerFormation.text.toString()

            if (firstName != null && lastName != null && mail != null && password != "" && promotion != null && formation != null) {
                var message: String? = null

                GlobalScope.launch(Dispatchers.Main) {
                    val deferred = async(Dispatchers.IO) {
                        //call requests
                        message = context?.let { it1 -> signUpViewModel.create(firstName,lastName,mail,password,promotion,formation,it1) }
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




