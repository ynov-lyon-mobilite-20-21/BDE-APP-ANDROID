package com.example.ynov_lyon_bde.domain.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO


class SignInViewModel : ViewModel() {

    suspend fun callApi(loginDto: LoginDTO, context: Context) {
        val userViewModel = AuthenticationViewModel()
        if (userViewModel.callLoginRequest(loginDto, context)) {
            userViewModel.callInformationUserRequest(context)
        }
    }
}
