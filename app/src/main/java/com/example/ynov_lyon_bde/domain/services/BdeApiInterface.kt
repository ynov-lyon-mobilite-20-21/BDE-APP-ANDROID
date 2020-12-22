package com.example.ynov_lyon_bde.domain.services

import com.example.ynov_lyon_bde.data.model.LoginDTO
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface BdeApiInterface {

    @POST("api/users")
    suspend fun createUser(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("api/auth")
    fun loginUser(@Body login: LoginDTO): Call<LoginDTO>
}
