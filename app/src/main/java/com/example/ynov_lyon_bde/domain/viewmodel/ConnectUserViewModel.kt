package com.example.ynov_lyon_bde.domain.viewmodel

import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.domain.services.BdeApiService


class ConnectUserViewModel {

    suspend fun signIn(loginDto: LoginDTO): MutableList<String> {
        val apiService = BdeApiService()
        return apiService.loginUser(loginDto)
    }

    suspend fun getUserInformations(token: String?): MutableList<String> {
        val apiService = BdeApiService()
        return apiService.getUser(token)
    }

    suspend fun refreshTokenUser(token: String?): MutableList<String> {
        val apiService = BdeApiService()
        return apiService.refreshToken(token)
    }
}
