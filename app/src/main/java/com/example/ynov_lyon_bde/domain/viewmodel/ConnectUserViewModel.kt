package com.example.ynov_lyon_bde.domain.viewmodel

import android.content.Context
import com.example.ynov_lyon_bde.data.model.LoginDTO


class ConnectUserViewModel {

    suspend fun callApi(loginDto: LoginDTO, context: Context) {
        val userViewModel = AuthenticationViewModel()
        if (userViewModel.callLoginRequest(loginDto, context)) {
            userViewModel.callInformationUserRequest(context)
        }
    }
}
