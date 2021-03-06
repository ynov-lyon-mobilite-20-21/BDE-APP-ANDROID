package com.example.ynov_lyon_bde.domain.viewmodel.signIn


import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO
import com.example.ynov_lyon_bde.domain.services.request.AuthenticationRequests
import com.example.ynov_lyon_bde.domain.services.request.BdeApiService
import com.example.ynov_lyon_bde.domain.utils.JsonServiceBuilder
import org.json.JSONObject

class SignInViewModel : ViewModel() {

    suspend fun login(mail: String, password: String, context : Context) : String? {
        var message : String? = null
        val loginDto = LoginDTO(
            mail = mail,
            password = password
        )
        val authenticationRequests = AuthenticationRequests()
        try{
            if (authenticationRequests.callLoginRequest(loginDto, context)) {
                authenticationRequests.meAndRefreshToken(context)
            }
        }
        catch (err: Exception) {
            Log.e("message", err.message)
            message = gestionMessageErr(err.message)
        }
        return message
    }

    private fun gestionMessageErr(message: String?): String? {
        var messageForUser : String? = null
        when(message){
            "" -> messageForUser = "Erreur"
            "NO_USER" -> messageForUser = "Email ou mot de passe incorrect"
            "USER_INACTIVE" -> messageForUser = "Veuillez valider votre adresse email"
            "INVALID_TOKEN" -> messageForUser = "Compte non valide"
            "USER_DOESNT_EXIST" -> messageForUser = "Email ou mot de passe incorrect"
            "BAD_CREDENTIALS" -> messageForUser = "Formulaire mal renseigné"
        }
        return messageForUser ?: message
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
}
