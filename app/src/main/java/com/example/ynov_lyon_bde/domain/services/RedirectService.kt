package com.example.ynov_lyon_bde.domain.services

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.ynov_lyon_bde.domain.services.request.AuthenticationRequests
import com.example.ynov_lyon_bde.ui.screens.connection.signIn.SignInFragment
import com.example.ynov_lyon_bde.ui.screens.profil.ProfileFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class RedirectService {

    fun redirect(context: Context): Intent {
        val authenticationRequests = AuthenticationRequests()
        var intent = Intent().setClass(context, SignInFragment::class.java)
        val sharedPreferencesService = SharedPreferencesService()

        if (verifStorageUser(context)) {
            intent = Intent().setClass(context, ProfileFragment::class.java)
        } else {
            if (sharedPreferencesService.retrived("TOKEN", context).isNullOrEmpty()) {
               intent = Intent().setClass(context, SignInFragment::class.java)
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    val deferred = async(Dispatchers.IO) {
                        //call requests
                        try {
                            authenticationRequests.meAndRefreshToken(context)
                        } catch (err: Exception) {
                            intent = Intent().setClass(context, SignInFragment::class.java)
                            Log.e("error", err.message)
                        }
                    }
                    deferred.await()
                }
            }
        }
        Log.e("intent", intent.toString())
        return intent
    }

    private fun verifStorageUser(context: Context) : Boolean {
        val sharedPreferencesService = SharedPreferencesService()
        var userExist = false
        if (sharedPreferencesService.retrivedUser(context) != null){
            userExist = true
        }
        return userExist
    }
}
