package com.example.ynov_lyon_bde.domain.viewmodel

import android.content.Context
import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.data.model.UserDTO


class CreateUserViewModel {

    suspend fun callApi(userDto: UserDTO, loginDto: LoginDTO, context: Context) {
        val userViewModel = AuthenticationViewModel()
        if (userViewModel.callRegisterRequest(userDto)) {
            if (userViewModel.callLoginRequest(loginDto, context)) {
                userViewModel.callInformationUserRequest(context)
            }
        }
    }

}
