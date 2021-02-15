package com.example.ynov_lyon_bde.domain.viewmodel.signIn


import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO
import com.example.ynov_lyon_bde.domain.services.request.BdeApiService
import com.example.ynov_lyon_bde.domain.utils.JsonServiceBuilder
import org.json.JSONObject

class SignInViewModel {

    private val bdeApiService = BdeApiService()

    suspend fun login(mail: String, password: String) {
        val loginDto = LoginDTO(
            mail = mail,
            password = password
        )
        val request = bdeApiService.call(BdeApiService.NameRequest.LOGIN, loginDto, token = null)
        if(request.isSuccessful) {
            val prettyJson = JsonServiceBuilder().convertRawToPrettyJson(request.body())
            val connectUserJson = prettyJson.split(";")[1]
            val token = JSONObject(connectUserJson).getJSONObject("data").getString("token")
        }
    }
}
