package com.example.ynov_lyon_bde.domain.viewmodel

import android.util.Log
import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.domain.services.BdeApiService
import retrofit2.Call
import retrofit2.Response

class ConnectUserViewModel {

    suspend fun signIn(mail: String, password: String): String? {
        val apiService = BdeApiService()
        val loginDto = LoginDTO(
            mail = mail,
            password = password)

        return apiService.loginUser(loginDto)
    }
}
