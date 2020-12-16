package com.example.ynov_lyon_bde.domain.services

import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.data.model.User
import com.example.ynov_lyon_bde.data.model.UserDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface BdeApiInterface {

    @POST("api/users")
    fun createUser(@Body userData: UserDTO): Call<UserDTO>

    @POST("api/auth")
    fun loginUser(@Body login: LoginDTO): Call<User>
}
