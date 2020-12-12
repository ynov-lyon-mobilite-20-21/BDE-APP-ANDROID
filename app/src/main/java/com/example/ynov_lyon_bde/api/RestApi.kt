package com.example.ynov_lyon_bde.api

import com.example.ynov_lyon_bde.model.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("users")
    fun addUser(@Body userData: UserInfo): Call<UserInfo>

    @POST("auth")
    fun loginUser(@Body login: UserInfo): Call<UserInfo>

}
