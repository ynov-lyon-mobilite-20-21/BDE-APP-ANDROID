package com.example.ynov_lyon_bde.domain.services

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.ynov_lyon_bde.domain.viewmodel.AuthenticationViewModel
import com.example.ynov_lyon_bde.ui.screens.ConnectUserActivity
import com.example.ynov_lyon_bde.ui.screens.CreateUserActivity
import com.example.ynov_lyon_bde.ui.screens.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class RedirectService {

    fun redirect(context: Context): Intent {
        val authenticationViewModel = AuthenticationViewModel()
        var intent = Intent().setClass(context, ConnectUserActivity::class.java)

        val sharedPreferencesService = SharedPreferencesService()
        var messageMe: String? = null
        var messageRefresh: String? = null

        if (sharedPreferencesService.retrived("TOKEN", context).isNullOrEmpty()) {
            intent = Intent().setClass(context, CreateUserActivity::class.java)
        }
        GlobalScope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                //call requests
                try {
                    authenticationViewModel.callInformationUserRequest(context)
                } catch (err: Exception) {
                    messageMe = err.message
                    Log.e("message", messageMe)
                }
                if (messageMe == "INVALID_TOKEN") {
                    try {
                        authenticationViewModel.callRefreshRequest(context)
                    } catch (err: Exception) {
                        messageRefresh = err.message
                        Log.e("message", messageRefresh)
                    }
                    if (messageRefresh.isNullOrEmpty()) {
                        try {
                            authenticationViewModel.callInformationUserRequest(context)
                            messageMe = null
                        } catch (err: Exception) {
                            messageMe = err.message
                            Log.e("message", messageMe)
                        }
                    }
                }
            }
            deferred.await()
            if (messageMe.isNullOrEmpty()) {
                //TODO : change to home activity
                val intent = Intent().setClass(context, MainActivity::class.java)
            }
            if (!messageRefresh.isNullOrEmpty()) {
                intent = Intent().setClass(context, ConnectUserActivity::class.java)
            }

        }
        return intent
    }

}
