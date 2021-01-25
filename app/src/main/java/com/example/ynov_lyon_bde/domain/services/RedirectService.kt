package com.example.ynov_lyon_bde.domain.services

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.ynov_lyon_bde.domain.services.request.AuthenticationRequests
import com.example.ynov_lyon_bde.ui.screens.connection.signIn.SignInFragment
//import com.example.ynov_lyon_bde.ui.screens.CreateUserActivity
import com.example.ynov_lyon_bde.ui.screens.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class RedirectService {

    fun redirect(context: Context): Intent {
        val authenticationViewModel = AuthenticationRequests()
        var intent = Intent().setClass(context, SignInFragment::class.java)
        val sharedPreferencesService = SharedPreferencesService()

        var errorGetUser: String? = null
        var errorRefresh: String? = null

        if (sharedPreferencesService.retrived("TOKEN", context).isNullOrEmpty()) {
//            intent = Intent().setClass(context, CreateUserActivity::class.java)
        }else{
            GlobalScope.launch(Dispatchers.Main) {
                val deferred = async(Dispatchers.IO) {
                    //call requests
                    try {
                        authenticationViewModel.callInformationUserRequest(context)
                    } catch (err: Exception) {
                        errorGetUser = err.message
                        Log.e("error", errorGetUser)
                    }
                    if (errorGetUser == "INVALID_TOKEN") {
                        try {
                            authenticationViewModel.callRefreshRequest(context)
                        } catch (err: Exception) {
                            errorRefresh = err.message
                            Log.e("error", errorRefresh)
                        }
                        if (errorRefresh.isNullOrEmpty()) {
                            try {
                                authenticationViewModel.callInformationUserRequest(context)
                                errorGetUser = null
                            } catch (err: Exception) {
                                errorGetUser = err.message
                                Log.e("error", errorGetUser)
                            }
                        }
                    }
                }
                deferred.await()
            }
            if (errorGetUser == null) {
                //TODO : change to home activity
                intent = Intent().setClass(context, MainActivity::class.java)
            }
            if (errorRefresh != null) {
                intent = Intent().setClass(context, SignInFragment::class.java)
            }
        }

        Log.e("intent", intent.toString())
        return intent
    }

}
