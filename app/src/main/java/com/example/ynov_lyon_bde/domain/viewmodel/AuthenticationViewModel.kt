package com.example.ynov_lyon_bde.domain.viewmodel

import android.content.Context
import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.domain.services.BdeApiService
import com.example.ynov_lyon_bde.domain.services.SharedPreferencesService
import org.json.JSONObject
import java.lang.Exception

class AuthenticationViewModel {

    private val sharedPreferencesService = SharedPreferencesService()

    //REGISTER REQUEST
    suspend fun callRegisterRequest(userDto: UserDTO): Boolean {
        val apiService = BdeApiService()
        val resultRequestCreateUser = apiService.apiCaller(BdeApiService.RequestType.USER, userDto)
        return apiService.handleException(resultRequestCreateUser, BdeApiService.ErrorType.ERROR)
    }

    //LOGIN REQUEST
    suspend fun callLoginRequest(loginDto: LoginDTO, context: Context): Boolean {
        //access to view model and service
        val apiService = BdeApiService()

        var tokenOfConnect: String?

        val resultRequestLogin = apiService.apiCaller(BdeApiService.RequestType.LOGIN, loginDto)
        val success = apiService.handleException(resultRequestLogin, BdeApiService.ErrorType.ERROR)
        val connectUserJson = resultRequestLogin?.split(";")?.get(1)
        tokenOfConnect = JSONObject(connectUserJson).getJSONObject("data").getString("token")

        //Save token in shared preference
        sharedPreferencesService.saveIn("TOKEN",
            tokenOfConnect!!,
            context)
        return success
    }

    //ME REQUEST
    suspend fun callInformationUserRequest(context: Context): Boolean {
        //access to view model and service
        val apiService = BdeApiService()

        val tokenOfConnect = sharedPreferencesService.retrived("TOKEN", context)
        if (tokenOfConnect.isNullOrEmpty()) {
            throw Exception("Aucun token")
        }
        val resultUserInformations =
            apiService.apiCaller(BdeApiService.RequestType.ME, tokenOfConnect)
        val success =
            apiService.handleException(resultUserInformations, BdeApiService.ErrorType.CODE)

        //TODO : create repository for store user

        return success
    }

    //REFRESH REQUEST
    suspend fun callRefreshRequest(context: Context): Boolean {
        val apiService = BdeApiService()

        val tokenToRefresh = sharedPreferencesService.retrived("TOKEN", context)
        if (tokenToRefresh.isNullOrEmpty()) {
            throw Exception("Aucun token")
        }

        val resultRequestRefreshUser =
            apiService.apiCaller(BdeApiService.RequestType.REFRESH, tokenToRefresh)
        return apiService.handleException(resultRequestRefreshUser, BdeApiService.ErrorType.ERROR)
    }
}
