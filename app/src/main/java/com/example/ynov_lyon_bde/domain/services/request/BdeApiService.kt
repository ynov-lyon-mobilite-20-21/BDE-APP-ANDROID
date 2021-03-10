package com.example.ynov_lyon_bde.domain.services.request

import com.example.ynov_lyon_bde.domain.utils.Constants.Companion.MEDIA_TYPE_JSON
import com.example.ynov_lyon_bde.domain.utils.JsonServiceBuilder
import com.example.ynov_lyon_bde.domain.utils.RetrofitServiceBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class BdeApiService {

    enum class NameRequest {
        REFRESH, ME, USER, LOGIN
    }

    suspend fun <T> apiCaller(nameRequest: NameRequest, body: T?, token:T?): String {
        val retrofit = RetrofitServiceBuilder.buildService(RetrofitApiInterface::class.java)
        var response: Response<ResponseBody>? = null
        when(nameRequest){
            NameRequest.USER ->{
                val requestBody = postRequestWithDto(body)
                response = retrofit.createUser(requestBody)
            }
            NameRequest.LOGIN ->{
                val requestBody = postRequestWithDto(body)
                response = retrofit.loginUser(requestBody)
            }
            NameRequest.REFRESH ->{
                val requestBody = postRequestWithOneProperty("refreshToken",body.toString())
                response = retrofit.refreshToken(requestBody)
            }
            NameRequest.ME ->{
                response = retrofit.getUser("Bearer $token")
            }
        }
        return sendResponseBody(response)
    }

    private fun sendResponseBody(response : Response<ResponseBody>): String {
        return if (response.isSuccessful) {
            "${response.code()};${convertResponseBodyToString(response.body())}"
        } else {
            "${response.code()};${convertResponseBodyToString(response.errorBody())}"
        }
    }

    private fun convertResponseBodyToString(response : ResponseBody?): String{
        return JsonServiceBuilder().convertRawToPrettyJson(response)
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

}



