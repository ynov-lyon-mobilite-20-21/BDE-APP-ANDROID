package com.example.ynov_lyon_bde.domain.viewmodel

import android.util.Log
import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.domain.services.BdeApiService

class CreateUserViewModel {

    fun signUp(firstName: String, lastName: String, email: String, password: String, promotion: String, formation: String, pictureUrl:String) {
        val apiService = BdeApiService()
        val userDto = UserDTO(
            firstName = firstName,
            lastName = lastName,
            mail = email,
            password = password,
            promotion = promotion,
            formation = formation,
            pictureUrl = pictureUrl)

        apiService.createUser(userDto) {
            Log.d("RESPONSE CODE ", it.code().toString())
            Log.d("RESPONSE MESSAGE ", it.message())
        }
    }
}
