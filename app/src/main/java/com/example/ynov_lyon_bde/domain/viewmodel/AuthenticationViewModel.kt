package com.example.ynov_lyon_bde.domain.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.ynov_lyon_bde.data.model.DTO.LoginDTO
import com.example.ynov_lyon_bde.data.model.DTO.UserDTO
import com.example.ynov_lyon_bde.data.repository.TestRepository
import com.example.ynov_lyon_bde.domain.services.BdeApiService
import com.example.ynov_lyon_bde.domain.services.SharedPreferencesService
import org.json.JSONObject
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//TODO nom de classe à renomer
@KoinApiExtension
class AuthenticationViewModel() : ViewModel(), KoinComponent {

    private val sharedPreferencesService = SharedPreferencesService()
    private val testRepository: TestRepository by inject()

    //REGISTER REQUEST
    suspend fun callRegisterRequest(userDto: UserDTO): Boolean {
        val resultRequestCreateUser =
            testRepository.bdeApiService.apiCaller(BdeApiService.RequestType.USER, userDto)
        return testRepository.bdeApiService.handleException(
            resultRequestCreateUser,
            BdeApiService.ErrorType.ERROR
        )
    }

    //LOGIN REQUEST
    suspend fun callLoginRequest(loginDto: LoginDTO, context: Context): Boolean {
        //access to view model and service

        var tokenOfConnect: String?

        val resultRequestLogin =
            testRepository.bdeApiService.apiCaller(BdeApiService.RequestType.LOGIN, loginDto)
        val success = testRepository.bdeApiService.handleException(
            resultRequestLogin,
            BdeApiService.ErrorType.ERROR
        )
        val connectUserJson = resultRequestLogin?.split(";")?.get(1)
        tokenOfConnect = JSONObject(connectUserJson).getJSONObject("data").getString("token")

        //Save token in shared preference
        sharedPreferencesService.saveIn(
            "TOKEN",
            tokenOfConnect!!,
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
            testRepository.bdeApiService.apiCaller(BdeApiService.RequestType.ME, tokenOfConnect)
        val success =
            testRepository.bdeApiService.handleException(
                resultUserInformations,
                BdeApiService.ErrorType.CODE
            )

        //TODO : create repository for store user

        return success
    }

    //REFRESH REQUEST
    suspend fun callRefreshRequest(context: Context): Boolean {

        val tokenToRefresh = sharedPreferencesService.retrived("TOKEN", context)
        if (tokenToRefresh.isNullOrEmpty()) {
            throw Exception("Aucun token")
        }

        val resultRequestRefreshUser =
            testRepository.bdeApiService.apiCaller(
                BdeApiService.RequestType.REFRESH,
                tokenToRefresh
            )
        return testRepository.bdeApiService.handleException(
            resultRequestRefreshUser,
            BdeApiService.ErrorType.ERROR
        )
    }
}
