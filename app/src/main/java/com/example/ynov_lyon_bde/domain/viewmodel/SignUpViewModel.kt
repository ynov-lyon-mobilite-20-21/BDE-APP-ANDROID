package com.example.ynov_lyon_bde.domain.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO
import com.example.ynov_lyon_bde.data.model.DTO.UserDTO


class SignUpViewModel : ViewModel() {

    suspend fun callApi(userDto: UserDTO, loginDto: LoginDTO, context: Context) {
        val userViewModel = AuthenticationViewModel()
        if (userViewModel.callRegisterRequest(userDto)) {
            if (userViewModel.callLoginRequest(loginDto, context)) {
                userViewModel.callInformationUserRequest(context)
            }
        }
    }

}
