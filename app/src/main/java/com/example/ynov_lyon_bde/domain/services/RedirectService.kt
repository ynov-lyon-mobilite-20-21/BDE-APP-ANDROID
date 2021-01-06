package com.example.ynov_lyon_bde.domain.services

import android.content.Context
import android.content.Intent
import com.example.ynov_lyon_bde.domain.viewmodel.ConnectUserViewModel
import com.example.ynov_lyon_bde.ui.screens.ConnectUserActivity
import com.example.ynov_lyon_bde.ui.screens.CreateUserActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject

class RedirectService {

    fun redirect(context: Context): Intent {
        val sharedPreferencesService = SharedPreferencesService()
        var token: String?
        var intent: Intent = Intent().setClass(context, ConnectUserActivity::class.java)
        val connectUserViewModel = ConnectUserViewModel()

        //If user exist
        if (sharedPreferencesService.retrived("TOKEN", context) != null) {
            token = sharedPreferencesService.retrived("TOKEN", context)
            //Request info user
            GlobalScope.launch(Dispatchers.Main) {
                async(Dispatchers.IO) {
                    //Information user request
                    var resultUserInformations = connectUserViewModel.getUserInformations(token)

                    var jsonResultRqInfo = JSONObject(resultUserInformations[1])
                    if (resultUserInformations[0].toInt() in 200..299) {
                        //TODO : create repository for store user
                        //TODO : go to home activity

                    } else if (jsonResultRqInfo.getString("code") == "INVALID_TOKEN") {
                        //Request refresh token
                        val resultRqRefresh = connectUserViewModel.refreshTokenUser(token)

                        val jsonResultRqRefresh = JSONObject(resultRqRefresh[1])
                        if (resultRqRefresh[0].toInt() in 200..299) {
                            //Information user request
                            resultUserInformations = connectUserViewModel.getUserInformations(token)

                            jsonResultRqInfo = JSONObject(resultUserInformations[1])
                            if (resultUserInformations[0].toInt() in 200..299) {
                                //TODO : create repository for store user
                                //TODO : go to home activity
                            }else{
                                //Go to connexion activity
                                intent = Intent().setClass(context, ConnectUserActivity::class.java)
                            }
                        }
                    }
                }
            }
        //User don't exist
        } else {
            //Go to inscription activity
            intent = Intent().setClass(context, CreateUserActivity::class.java)
        }
        return intent
    }

}
