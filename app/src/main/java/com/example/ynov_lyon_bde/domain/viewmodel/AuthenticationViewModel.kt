    package com.example.ynov_lyon_bde.domain.viewmodel

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO
import com.example.ynov_lyon_bde.data.model.DTO.UserDTO
import com.example.ynov_lyon_bde.domain.services.request.AuthenticationRequests

class AuthenticationViewModel : ViewModel() {

    fun checkMail(email:String): String? {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email
        } else {
            null
        }
    }

    fun checkNames(email: String): MutableList<String>? {
        val names: MutableList<String> = mutableListOf()
        val emailSplit: String = email.split('@')[0]
        return if(emailSplit.contains(".")){
            names.add(emailSplit.split('.')[0])
            names.add(emailSplit.split('.')[1])
            names
        }else{
            null
        }
    }

    fun spinnerInformed(txtSpinner : MutableList<String>): Boolean {
        var spinnerInformed = true
        for (item in txtSpinner){
            if(item == null || item == "" || item == "Classe" || item == "Filière"){
                spinnerInformed = false
            }
        }
        return spinnerInformed
    }

    fun showHideBehaviour(editText: EditText, imageView: ImageView){
        if(editText.transformationMethod == PasswordTransformationMethod.getInstance()){
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imageView.setImageResource(R.drawable.hide)
        } else{
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            imageView.setImageResource(R.drawable.show)
        }
    }

    suspend fun callApiSignUp(userDto: UserDTO, loginDto: LoginDTO, context: Context) {
        val userViewModel = AuthenticationRequests()
        if (userViewModel.callRegisterRequest(userDto)) {
            if (userViewModel.callLoginRequest(loginDto, context)) {
                userViewModel.callInformationUserRequest(context)
            }
        }
    }

    suspend fun callApiSignIn(loginDto: LoginDTO, context: Context) {
        val userViewModel = AuthenticationRequests()
        if (userViewModel.callLoginRequest(loginDto, context)) {
            userViewModel.callInformationUserRequest(context)
        }
    }

}
