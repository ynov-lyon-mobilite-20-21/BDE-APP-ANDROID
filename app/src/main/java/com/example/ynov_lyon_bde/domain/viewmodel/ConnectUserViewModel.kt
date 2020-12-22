package com.example.ynov_lyon_bde.domain.viewmodel

import android.util.Log
import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.domain.services.BdeApiService
import retrofit2.Call
import retrofit2.Response

class ConnectUserViewModel {

    fun signIn(email: String, password: String): Boolean {
        val apiService = BdeApiService()
        val loginDto = LoginDTO(
            email = email,
            password = password)

        var result = ""

        var isSuccess = false
        apiService.loginUser(loginDto) {

            if(it.code() >=200 && it.code() <= 226){
                isSuccess = true
            }

            Log.d("RESPONSE CODE ", it.code().toString())
            Log.d("RESPONSE MESSAGE ", it.message())

        }
        println("////////////////////"+isSuccess)
        return isSuccess
    }
}
