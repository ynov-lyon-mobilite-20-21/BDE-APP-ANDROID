package com.example.ynov_lyon_bde.domain.viewmodel.signUp

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO
import com.example.ynov_lyon_bde.data.model.DTO.UserDTO
import com.example.ynov_lyon_bde.domain.services.request.AuthenticationRequests
import java.lang.Exception

class SignUpViewModel : ViewModel() {

    fun spinnerInformed(txtSpinner : MutableList<String>): Boolean {
        var spinnerInformed = true
        for (item in txtSpinner){
            if(item == "" || item == "Classe" || item == "Filière"){
                spinnerInformed = false
            }
        }
        return spinnerInformed
    }

    fun showHideBehaviour(editText: EditText, imageView: ImageView){
        if(editText.transformationMethod == PasswordTransformationMethod.getInstance()){
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imageView.setImageResource(R.drawable.userconnect_illustration_noshow_password)
        } else{
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            imageView.setImageResource(R.drawable.userconnect_illustration_show_password)
        }
    }

    suspend fun create(lastName: String, firstName:String, mail: String, password: String, promotion: String, formation: String, context : Context):String? {
        var message : String? = null
        val userDto = UserDTO(
            firstName = firstName,
            lastName = lastName,
            mail = mail,
            password = password,
            promotion = promotion,
            formation = formation
        )
        val loginDto = LoginDTO(
            mail = mail,
            password = password
        )
        val authenticationRequests = AuthenticationRequests()
        try{
            if (authenticationRequests.callRegisterRequest(userDto)) {
                if (authenticationRequests.callLoginRequest(loginDto, context)) {
                    authenticationRequests.meAndRefreshToken(context)
                }
            }
        }catch(err : Exception){
            Log.e("message", err.message)
            message = gestionMessageErr(err.message)
        }
        return message
    }

    private fun gestionMessageErr(message: String?): String? {
        var messageForUser : String? = null
        when(message){
            "UNKNOWN_ERROR" -> messageForUser = "Erreur"
            "NO_USER" -> messageForUser = "Email ou mot de passe incorrect"
            "USER_INACTIVE" -> messageForUser = "Veuillez valider votre adresse email"
            "INVALID_TOKEN" -> messageForUser = "Compte non valide"
            "USER_DOESNT_EXIST" -> messageForUser = "Email ou mot de passe incorrect"
            "BAD_CREDENTIALS" -> messageForUser = "Formulaire mal renseigné"
            "EMAIL_REQUIRED" -> messageForUser = "Email non renseigné"
            "PASSWORD_REQUIRED" -> messageForUser = "Mot de passe non renseigné"
            "FIRSTNAME_REQUIRED" -> messageForUser = "Prénom non renseigné"
            "LASTNAME_REQUIRED" -> messageForUser = "Nom non renseigné"
            "PROMOTION_REQUIRED" -> messageForUser = "Promotion non renseignée"
            "FORMATION_REQUIRED" -> messageForUser = "Formation non renseignée"
            "USER_ALREADY_EXISTS" -> messageForUser = "Ce compte existe déjà"
        }
        return messageForUser
    }


}
