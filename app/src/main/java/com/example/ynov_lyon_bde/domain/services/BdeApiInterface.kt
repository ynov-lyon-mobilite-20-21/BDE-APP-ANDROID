package com.example.ynov_lyon_bde.domain.services

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface BdeApiInterface {

    @POST("api/users")
    suspend fun createUser(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("api/auth")
    suspend fun loginUser(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("api/me")
    suspend fun getUser(@Header("Authorization") token: String?): Response<ResponseBody>

    @POST("api/auth/refresh")
    suspend fun refreshToken(@Body requestBody: RequestBody): Response<ResponseBody>
}
