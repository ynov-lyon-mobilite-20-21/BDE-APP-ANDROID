package com.example.ynov_lyon_bde.domain.services


import android.util.Log
import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.domain.utils.JsonService
import com.example.ynov_lyon_bde.domain.utils.RetrofitServiceBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BdeApiService {
    suspend fun createUser(userData: UserDTO): String? {
        var resultRequest : String? = null
        val retrofit = RetrofitServiceBuilder.buildService(BdeApiInterface::class.java)
        // Create JSON using JSONObject

        val jsonObject = JSONObject()
        jsonObject.put("firstName", userData.firstName)
        jsonObject.put("lastName", userData.lastName)
        jsonObject.put("mail", userData.mail)
        jsonObject.put("password", userData.password)
        jsonObject.put("promotion", userData.promotion)
        jsonObject.put("formation", userData.formation)
        jsonObject.put("pictureUrl", userData.pictureUrl)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody
        val requestBody  = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            // Do the POST request and get response
            val response = retrofit.createUser(requestBody)

                if (response.isSuccessful) {
                    val prettyJson = JsonService().ConvertRawToPrettyJson(response)

                    Log.d("Pretty Printed JSON :", prettyJson)

                    resultRequest = prettyJson

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())
                    resultRequest = response.code().toString()

                }

        return resultRequest
    }

    fun loginUser(userData: LoginDTO, onResult: (Response<LoginDTO>) -> Unit){
        val retrofit = RetrofitServiceBuilder.buildService(BdeApiInterface::class.java)
        retrofit.loginUser(userData).enqueue(
            object : Callback<LoginDTO> {
                override fun onFailure(call: Call<LoginDTO>, t: Throwable) {
                    print("THROWABLE : $t")
                    return
                }

                override fun onResponse(call: Call<LoginDTO>, response: Response<LoginDTO>) {
                    val connectedUser = response.body()
                    onResult(response)

                }
            }
        )
    }

}



