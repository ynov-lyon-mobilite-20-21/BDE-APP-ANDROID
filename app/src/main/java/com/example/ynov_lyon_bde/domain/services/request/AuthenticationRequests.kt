package com.example.ynov_lyon_bde.domain.services.request

import android.content.Context
import android.util.Log
import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO
import com.example.ynov_lyon_bde.data.model.DTO.UserDTO
import com.example.ynov_lyon_bde.data.model.User
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
        val response = bdeApiService.apiCaller(BdeApiService.NameRequest.USER, userDto, null)
        return errorManager.handleException(
            response,
            ErrorManager.ErrorType.ERROR
        )
    }

    //LOGIN REQUEST
    suspend fun callLoginRequest(loginDto: LoginDTO, context: Context): Boolean {
        //access to view model and service
        val token: String?
        val response = bdeApiService.apiCaller(BdeApiService.NameRequest.LOGIN, loginDto,null)
        val success = errorManager.handleException(
            response,
            ErrorManager.ErrorType.ERROR
        )
        val connectUserJson = response.split(";")[1]
        token = JSONObject(connectUserJson).getJSONObject("data").getString("token")

        //Save token in shared preference
        sharedPreferencesService.saveIn("TOKEN", token!!, context)
        return success
    }

    //ME REQUEST
    suspend fun callInformationUserRequest(context: Context): Boolean {
        //access to view model and service
        val tokenOfConnect = sharedPreferencesService.retrived("TOKEN", context)
        if (tokenOfConnect.isNullOrEmpty()) {
            throw Exception("Aucun token")
        }
        val response = bdeApiService.apiCaller(BdeApiService.NameRequest.ME, null, tokenOfConnect)
        val success =
            errorManager.handleException(
                response,
                ErrorManager.ErrorType.CODE
            )

        //save User
        //Stock Informations user in Shared preferences
        Log.d("resultUserInformations", response)
        val resultMe = response.split(";")[1]
        val jsonResultRequest = JSONObject(resultMe)
        val user = User(jsonResultRequest.getJSONObject("data").getString("_id"),
            jsonResultRequest.getJSONObject("data").getBoolean("isActive"),
            jsonResultRequest.getJSONObject("data").getBoolean("isAdmin"),
            jsonResultRequest.getJSONObject("data").getBoolean("isAdherent"),
            jsonResultRequest.getJSONObject("data").getString("firstName"),
            jsonResultRequest.getJSONObject("data").getString("lastName"),
            jsonResultRequest.getJSONObject("data").getString("mail"),
            jsonResultRequest.getJSONObject("data").getString("promotion"),
            jsonResultRequest.getJSONObject("data").getString("formation"),
            jsonResultRequest.getJSONObject("data").getString("activationKey"))
        sharedPreferencesService.saveInUser(user, context)
        Log.d("User", sharedPreferencesService.retrivedUser(context).toString())
        return success
    }

    //REFRESH REQUEST
    suspend fun callRefreshRequest(context: Context): Boolean {
        val tokenToRefresh = sharedPreferencesService.retrived("TOKEN", context)
        if (tokenToRefresh.isNullOrEmpty()) {
            throw Exception("Aucun token")
        }
        val response = bdeApiService.apiCaller(BdeApiService.NameRequest.REFRESH, tokenToRefresh, null)
        return errorManager.handleException(
            response,
            ErrorManager.ErrorType.ERROR
        )
    }
}
