package com.example.ynov_lyon_bde.domain.utils

import com.example.ynov_lyon_bde.domain.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


object RetrofitServiceBuilder {
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        try{
            return retrofit.create(service)
        }catch (err:Exception){
            when(err) {
                is SocketTimeoutException -> {
                    //Server takes too long to respond
                    throw SocketTimeoutException("TimeOut")
                }
                is UnknownHostException -> {
                    //No connection and broken url / no server at all
                    throw err
                }
                is HttpException -> {
                    //server error response
                    throw err
                }
                else -> throw err
            }
        }finally{}
        return retrofit.create(service)

    }


}


