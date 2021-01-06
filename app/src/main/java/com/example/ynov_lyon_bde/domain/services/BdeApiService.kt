package com.example.ynov_lyon_bde.domain.services

import android.util.Log
import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.domain.utils.JsonServiceBuilder
import com.example.ynov_lyon_bde.domain.utils.RetrofitServiceBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


class BdeApiService {
    suspend fun createUser(userData: UserDTO): MutableList<String> {
        val resultRequest: MutableList<String> = arrayListOf()
        val retrofit = RetrofitServiceBuilder.buildService(BdeApiInterface::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("firstName", userData.firstName)
        jsonObject.put("lastName", userData.lastName)
        jsonObject.put("mail", userData.mail)
        jsonObject.put("password", userData.password)
        jsonObject.put("promotion", userData.promotion)
        jsonObject.put("formation", userData.formation)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        // Do the POST request and get response
        val response = retrofit.createUser(requestBody)
        if (response.isSuccessful) {
            val prettyJson = JsonServiceBuilder().convertRawToPrettyJson(response)
            Log.d("Pretty Printed JSON :", prettyJson)
            resultRequest.add(response.code().toString())
            resultRequest.add(prettyJson)
        } else {
            val prettyJson = JsonServiceBuilder().convertRawToPrettyJsonErr(response.errorBody())
            Log.e("RETROFIT_ERROR", prettyJson)
            resultRequest.add(response.code().toString())
            resultRequest.add(prettyJson)
        }

        return resultRequest
    }

    suspend fun loginUser(loginData: LoginDTO): MutableList<String> {
        val resultRequest: MutableList<String> = arrayListOf()
        val retrofit = RetrofitServiceBuilder.buildService(BdeApiInterface::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("mail", loginData.mail)
        jsonObject.put("password", loginData.password)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        // Do the POST request and get response
        val response = retrofit.loginUser(requestBody)
        if (response.isSuccessful) {
            val prettyJson = JsonServiceBuilder().convertRawToPrettyJson(response)
            Log.d("Pretty Printed JSON :", prettyJson)
            resultRequest.add(response.code().toString())
            resultRequest.add(prettyJson)
        } else {
            val prettyJson = JsonServiceBuilder().convertRawToPrettyJsonErr(response.errorBody())
            Log.e("RETROFIT_ERROR", prettyJson)
            resultRequest.add(response.code().toString())
            resultRequest.add(prettyJson)
        }
        return resultRequest
    }

    suspend fun refreshToken(token: String?): MutableList<String> {
        val resultRequest: MutableList<String> = arrayListOf()
        val retrofit = RetrofitServiceBuilder.buildService(BdeApiInterface::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("refreshToken", token)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        // Do the POST request and get response
        val response = retrofit.refreshToken(requestBody)

        if (response.isSuccessful) {
            val prettyJson = JsonServiceBuilder().convertRawToPrettyJson(response)
            Log.d("Pretty Printed JSON :", prettyJson)
            resultRequest.add(response.code().toString())
            resultRequest.add(prettyJson)

        } else {
            val prettyJson = JsonServiceBuilder().convertRawToPrettyJsonErr(response.errorBody())
            Log.e("RETROFIT_ERROR", response.code().toString())
            resultRequest.add(response.code().toString())
            resultRequest.add(prettyJson)
        }

        return resultRequest
    }

    suspend fun getUser(token: String?): MutableList<String> {
        val resultRequest: MutableList<String> = arrayListOf()
        val retrofit = RetrofitServiceBuilder.buildService(BdeApiInterface::class.java)

        // Do the POST request and get response
        val response = retrofit.getUser("Bearer $token")

        if (response.isSuccessful) {
            val prettyJson = JsonServiceBuilder().convertRawToPrettyJson(response)
            Log.d("Pretty Printed JSON :", prettyJson)
            resultRequest.add(response.code().toString())
            resultRequest.add(prettyJson)

        } else {
            val prettyJson = JsonServiceBuilder().convertRawToPrettyJsonErr(response.errorBody())
            Log.e("RETROFIT_ERROR", prettyJson)
            resultRequest.add(response.code().toString())
            resultRequest.add(prettyJson)
        }

        return resultRequest
    }

}



