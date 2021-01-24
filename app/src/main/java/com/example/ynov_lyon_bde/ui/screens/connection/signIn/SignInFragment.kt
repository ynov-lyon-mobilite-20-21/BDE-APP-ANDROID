package com.example.ynov_lyon_bde.ui.screens.connection.signIn

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO
import com.example.ynov_lyon_bde.domain.viewmodel.SignInViewModel
import com.example.ynov_lyon_bde.ui.screens.MainActivity
import kotlinx.android.synthetic.main.fragment_connectuser.*
import kotlinx.android.synthetic.main.fragment_connectuser.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//TODO les contraintes graohiques et le design du fragment_connectUser ne sont pas assez responsives, il faut reprendre le design pour qu'il puisse fonctionner
// avec le plus grand nombre de device et ce qu'il fasse 4" ou 6"
class SignInFragment : Fragment() {

    //TODO tu as de la logique ici qui devrait être dans un viewModel, fais attention à respecter l'archi MVVM
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_connectuser, container, false)

        //Show / Hide button
        view.showHideButton2.setOnClickListener {
            if(editTextPassword2.transformationMethod == PasswordTransformationMethod.getInstance()){
                editTextPassword2.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else{
                editTextPassword2.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        view.buttonCreateUser2.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        view.buttonConnect.setOnClickListener {
            val connectUserViewModel = SignInViewModel()

            // Take informations User
            val email = if (Patterns.EMAIL_ADDRESS.matcher(editTextMail2.text.toString()).matches()) {
                editTextMail2.text.toString()
            } else {
                null
            }
            val password = editTextPassword2.text.toString()

            //send request to api to connect user
            if (email != null && password != null) {
                var message: String? = null
                val loginDto = LoginDTO(
                    mail = email,
                    password = password
                )
                GlobalScope.launch(Dispatchers.Main) {
                    val deferred = async(Dispatchers.IO) {
                        //call requests
                        try {
                            context?.let { it1 -> connectUserViewModel.callApi(loginDto, it1) }
                        } catch (err: Exception) {
                            message = err.message
                            Log.e("message", message)
                        }
                    }
                    deferred.await()
                    if (message.isNullOrEmpty()) {
                        //TODO : Set intent with home activity
                        //val intent = context?.let { it1 -> Intent().setClass(it1, MainActivity::class.java) }
                        //startActivity(intent)
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
