package com.example.ynov_lyon_bde.domain.viewmodel

import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.domain.services.BdeApiService

class CreateUserViewModel {

    suspend fun signUp(firstName: String, lastName: String, email: String, password: String, promotion: String, formation: String): String? {
        val apiService = BdeApiService()
        val userDto = UserDTO(
            firstName = firstName,
            lastName = lastName,
            mail = email,
            password = password,
            promotion = promotion,
            formation = formation
        )


        return apiService.createUser(userDto)
    }
}
