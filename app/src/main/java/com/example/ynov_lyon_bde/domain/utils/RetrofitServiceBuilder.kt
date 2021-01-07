package com.example.ynov_lyon_bde.domain.utils

import com.example.ynov_lyon_bde.domain.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
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
        /*
        val request = retrofit.create(service)
        return if(client.readTimeoutMillis < 30000 && client.writeTimeoutMillis < 10000 && client.connectTimeoutMillis < 10000){
            request
        }else{
            null
        }*/
        return retrofit.create(service)
    }

}


