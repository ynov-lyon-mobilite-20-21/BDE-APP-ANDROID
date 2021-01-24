package com.example.ynov_lyon_bde.domain.services

import android.util.Log
import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO
import com.example.ynov_lyon_bde.data.model.DTO.UserDTO
import com.example.ynov_lyon_bde.domain.utils.Constants.Companion.MEDIA_TYPE_JSON
import com.example.ynov_lyon_bde.domain.utils.JsonServiceBuilder
import com.example.ynov_lyon_bde.domain.utils.RetrofitServiceBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception

class BdeApiService {

//Throw an error if json response of request is null or code request no enter 200 and 299
    fun handleException(apiResponse: String?, errorMessage: ErrorType): Boolean {
        var success: Boolean = true
        if (!apiResponse.isNullOrEmpty()) {
            val t = apiResponse.split(";")
            val code = t[0].toInt()
            val json = t[1]
            val jsonObject = JSONObject(t[1])

            if (json.isEmpty()) {
                throw Exception("Le Json récupéré est null")
            }

            if (code !in 200..299) {
                when (errorMessage) {
                    ErrorType.CODE ->
                        throw Exception(jsonObject.getString("code"))
                    ErrorType.ERROR ->
                        throw Exception(jsonObject.getJSONObject("error").getString("code"))
                }
                success = false
            }

        } else {
            throw Exception("la reponse reçue est null")
            success = false
        }
        return success
    }

    enum class ErrorType {
        CODE, ERROR
    }

    suspend fun <T> apiCaller(requestType: RequestType, propertyRequest: T): String? {
        var resultRequest: String? = null
        val retrofit = RetrofitServiceBuilder.buildService(BdeApiInterface::class.java)
        var response: Response<ResponseBody>? = null
        val jsonObject = JSONObject()

        when (propertyRequest) {
            is UserDTO -> {
                jsonObject.put("firstName", propertyRequest.firstName)
                jsonObject.put("lastName", propertyRequest.lastName)
                jsonObject.put("mail", propertyRequest.mail)
                jsonObject.put("password", propertyRequest.password)
                jsonObject.put("promotion", propertyRequest.promotion)
                jsonObject.put("formation", propertyRequest.formation)
                val requestBody =
                    jsonObject.toString().toRequestBody(MEDIA_TYPE_JSON.toMediaTypeOrNull())
                response = retrofit.createUser(requestBody)
            }
            is LoginDTO -> {
                jsonObject.put("mail", propertyRequest.mail)
                jsonObject.put("password", propertyRequest.password)
                val requestBody =
                    jsonObject.toString().toRequestBody(MEDIA_TYPE_JSON.toMediaTypeOrNull())
                response = retrofit.loginUser(requestBody)
            }
            is String -> {
                if (requestType == RequestType.REFRESH) {
                    jsonObject.put("refreshToken", propertyRequest)
                    val requestBody =
                        jsonObject.toString().toRequestBody(MEDIA_TYPE_JSON.toMediaTypeOrNull())
                    response = retrofit.refreshToken(requestBody)
                } else if (requestType == RequestType.ME) {
                    response = retrofit.getUser("Bearer $propertyRequest")
                }
            }
        }
        if (response != null) {
            if (response.isSuccessful) {
                val prettyJson = JsonServiceBuilder().convertRawToPrettyJson(response.body())
                Log.d("Pretty Printed JSON :", prettyJson)
                resultRequest = "${response.code()};$prettyJson"
            } else {
                val prettyJson = JsonServiceBuilder().convertRawToPrettyJson(response.errorBody())
                Log.e("RETROFIT_ERROR", prettyJson)
                resultRequest = "${response.code()};$prettyJson"
            }
        }
        return resultRequest
    }

    enum class RequestType {
        REFRESH, ME, USER, LOGIN
    }

}



