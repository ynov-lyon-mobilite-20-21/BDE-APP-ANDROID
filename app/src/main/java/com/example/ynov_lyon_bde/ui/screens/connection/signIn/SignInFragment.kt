package com.example.ynov_lyon_bde.ui.screens.connection.signIn

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.domain.viewmodel.AuthenticationViewModel
import com.example.ynov_lyon_bde.domain.viewmodel.signIn.SignInViewModel
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
        val authenticationViewModel = AuthenticationViewModel()
        val signInViewModel = SignInViewModel()

        //Show / Hide button
        view.showHideButton2.setOnClickListener {
            authenticationViewModel.showHideBehaviour(editTextPassword2, showHideButton2)
        }

        view.buttonCreateUser2.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        view.buttonConnect.setOnClickListener {
            // Take informations User
            val email = authenticationViewModel.checkMail(editTextMail2.text.toString())
            val password = editTextPassword2.text.toString()

            var message: String? = null
            if (email != null) {
                GlobalScope.launch(Dispatchers.Main) {
                    val deferred = async(Dispatchers.IO) {
                        //call requests
                        try {
                            signInViewModel.login(email, password)
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
            }

//            //send request to api to connect user
//            if (email != null && password != null) {
//                var message: String? = null
//                val loginDto = LoginDTO(
//                    mail = email,
//                    password = password
//                )
//                GlobalScope.launch(Dispatchers.Main) {
//                    val deferred = async(Dispatchers.IO) {
//                        //call requests
//                        try {
//                            context?.let { it1 -> authenticationViewModel.callApiSignIn(loginDto, it1) }
//                        } catch (err: Exception) {
//                            message = err.message
//                            Log.e("message", message)
//                        }
//                    }
//                    deferred.await()
//                    if (message.isNullOrEmpty()) {
//                        activity?.finish()
//                    } else {
//                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } else {
//                Toast.makeText(context, "Formulaire mal renseigné", Toast.LENGTH_SHORT).show()
//            }
        }

        return view
    }
}
