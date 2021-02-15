package com.example.ynov_lyon_bde.domain.services.request

import android.util.Log
import com.example.ynov_lyon_bde.domain.services.SharedPreferencesService
import com.example.ynov_lyon_bde.domain.utils.Constants
import com.example.ynov_lyon_bde.domain.utils.Constants.Companion.MEDIA_TYPE_JSON
import com.example.ynov_lyon_bde.domain.utils.JsonServiceBuilder
import com.example.ynov_lyon_bde.domain.utils.RetrofitServiceBuilder
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit

class BdeApiService {

    enum class NameRequest {
        REFRESH, ME, USER, LOGIN
    }

    suspend fun <T> call(nameRequest: NameRequest, body: T?, token: T): Response<ResponseBody> {
        val retrofit = RetrofitServiceBuilder.buildService(RetrofitApiInterface::class.java)
        val jsonTut: String = JsonServiceBuilder().dataClassObjectToJsonString(body)
        val requestBody: RequestBody = jsonTut.toRequestBody(MEDIA_TYPE_JSON.toMediaTypeOrNull())

        return when(nameRequest){
            NameRequest.USER ->{
                retrofit.createUser(requestBody)
            }
            NameRequest.LOGIN ->{
                retrofit.loginUser(requestBody)
            }
            NameRequest.REFRESH ->{
                retrofit.refreshToken(requestBody)
            }
            NameRequest.ME ->{
                retrofit.getUser("Bearer $token")
            }
        }
    }

    suspend fun <T> apiCaller(nameRequest: NameRequest, propertyRequest: T): String? {
        val retrofit = RetrofitServiceBuilder.buildService(RetrofitApiInterface::class.java)
        var response: Response<ResponseBody>? = null

        when(nameRequest){
            NameRequest.USER ->{
                val requestBody = postRequestWithDto(propertyRequest)
                response = retrofit.createUser(requestBody)
            }
            NameRequest.LOGIN ->{
                val requestBody = postRequestWithDto(propertyRequest)
                response = retrofit.loginUser(requestBody)
            }
            NameRequest.REFRESH ->{
                val requestBody = postRequestWithOneProperty("refreshToken",propertyRequest.toString())
                response = retrofit.refreshToken(requestBody)
            }
            NameRequest.ME ->{
                response = retrofit.getUser("Bearer $propertyRequest")
            }
        }
        return verifResponse(response)
    }

    private fun <T> postRequestWithDto(dto: T): RequestBody {
        val jsonTut: String = JsonServiceBuilder().dataClassObjectToJsonString(dto)
        return jsonTut.toRequestBody(MEDIA_TYPE_JSON.toMediaTypeOrNull())
    }

    private fun postRequestWithOneProperty(strBody : String, value : String) : RequestBody {
        val jsonObject = JSONObject()
        jsonObject.put(strBody, value)
        return jsonObject.toString().toRequestBody(MEDIA_TYPE_JSON.toMediaTypeOrNull())
    }

    private fun verifResponse(response : Response<ResponseBody>): String? {
        var resultRequest: String? = null
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

}



