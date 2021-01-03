package com.example.ynov_lyon_bde.domain.viewmodel

import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.domain.services.BdeApiService


class ConnectUserViewModel {

    suspend fun signIn(loginDto : LoginDTO): String? {
        val apiService = BdeApiService()
        return apiService.loginUser(loginDto)
    }

    suspend fun getUserInformations(token:String?) :String?{
        val apiService = BdeApiService()
        return apiService.getUser(token)
    }

    suspend fun refreshTokenUser(token:String?):String?{
        val apiService = BdeApiService()
        return apiService.refreshToken(token)
    }
}
