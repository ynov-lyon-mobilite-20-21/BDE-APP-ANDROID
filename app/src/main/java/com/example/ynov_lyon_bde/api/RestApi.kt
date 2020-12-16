package com.example.ynov_lyon_bde.api

import com.example.ynov_lyon_bde.model.LoginDTO
import com.example.ynov_lyon_bde.model.User
import com.example.ynov_lyon_bde.model.UserDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface RestApi {

    @POST("api/users")
    fun addUser(@Body userData: UserDTO): Call<UserDTO>

    @POST("api/auth")
    fun loginUser(@Body login: LoginDTO): Call<User>

}
