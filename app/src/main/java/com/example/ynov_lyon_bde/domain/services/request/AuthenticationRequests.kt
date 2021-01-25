package com.example.ynov_lyon_bde.domain.services.request

import android.content.Context
import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO
import com.example.ynov_lyon_bde.data.model.DTO.UserDTO
import com.example.ynov_lyon_bde.domain.services.SharedPreferencesService
import org.json.JSONObject
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@KoinApiExtension
class AuthenticationRequests() : KoinComponent {

    private val sharedPreferencesService = SharedPreferencesService()
    private val bdeApiService = BdeApiService()
    private val errorManager = ErrorManager()

    //REGISTER REQUEST
    suspend fun callRegisterRequest(userDto: UserDTO): Boolean {
        val resultRequestCreateUser =
            bdeApiService.apiCaller(BdeApiService.NameRequest.USER, userDto)
        return errorManager.handleException(
            resultRequestCreateUser,
            ErrorManager.ErrorType.ERROR
        )
    }

    //LOGIN REQUEST
    suspend fun callLoginRequest(loginDto: LoginDTO, context: Context): Boolean {
        //access to view model and service

        var token: String?

        val resultRequestLogin =
            bdeApiService.apiCaller(BdeApiService.NameRequest.LOGIN, loginDto)
        val success = errorManager.handleException(
            resultRequestLogin,
            ErrorManager.ErrorType.ERROR
        )
        val connectUserJson = resultRequestLogin?.split(";")?.get(1)
        token = JSONObject(connectUserJson).getJSONObject("data").getString("token")

        //Save token in shared preference
        sharedPreferencesService.saveIn(
            "TOKEN",
            token!!,
            context
        )
        return success
    }

    //ME REQUEST
    suspend fun callInformationUserRequest(context: Context): Boolean {
        //access to view model and service

        val tokenOfConnect = sharedPreferencesService.retrived("TOKEN", context)
        if (tokenOfConnect.isNullOrEmpty()) {
            throw Exception("Aucun token")
        }
        val resultUserInformations =
            bdeApiService.apiCaller(BdeApiService.NameRequest.ME, tokenOfConnect)
        val success =
            errorManager.handleException(
                resultUserInformations,
                ErrorManager.ErrorType.CODE
            )

        //TODO : store user

        return success
    }

    //REFRESH REQUEST
    suspend fun callRefreshRequest(context: Context): Boolean {

        val tokenToRefresh = sharedPreferencesService.retrived("TOKEN", context)
        if (tokenToRefresh.isNullOrEmpty()) {
            throw Exception("Aucun token")
        }

        val resultRequestRefreshUser =
            bdeApiService.apiCaller(
                BdeApiService.NameRequest.REFRESH,
                tokenToRefresh
            )
        return errorManager.handleException(
            resultRequestRefreshUser,
            ErrorManager.ErrorType.ERROR
        )
    }
}
