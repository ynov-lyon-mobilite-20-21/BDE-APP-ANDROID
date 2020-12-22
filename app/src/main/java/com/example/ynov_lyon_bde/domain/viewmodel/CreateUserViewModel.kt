package com.example.ynov_lyon_bde.domain.viewmodel

import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.domain.services.BdeApiService

class CreateUserViewModel {

    suspend fun signUp(userData: UserDTO): String? {
        val apiService = BdeApiService()
        return apiService.createUser(userData)
    }
}
