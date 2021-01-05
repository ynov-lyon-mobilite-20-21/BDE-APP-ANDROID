package com.example.ynov_lyon_bde.ui.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.data.model.User
import com.example.ynov_lyon_bde.domain.services.SharedPreferencesService
import com.example.ynov_lyon_bde.domain.viewmodel.ConnectUserViewModel
import kotlinx.android.synthetic.main.activity_connectuser.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject


class ConnectUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connectuser)

        //return previous activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonCreateUser2.setOnClickListener {
            //Go to inscription activity
            val intent = Intent().setClass(this, CreateUserActivity::class.java)
            startActivity(intent)
        }

        buttonConnect.setOnClickListener {
            val connectUserViewModel = ConnectUserViewModel()
            val sharedPreferencesService = SharedPreferencesService()

            // Take informations User
            val email = if (Patterns.EMAIL_ADDRESS.matcher(editTextMail2.text.toString())
                    .matches()
            ) editTextMail2.text.toString() else null
            val password = editTextPassword2.text.toString()

            //send request to api to connect user
            if (email != null && password != null) {
                var resultRequestToken : String? = null
                var message : String? = null
                val loginDto = LoginDTO(
                    mail = email,
                    password = password
                )
                GlobalScope.launch(Dispatchers.Main) {
                    val deferred = async(Dispatchers.IO) {
                        val resultRequest = connectUserViewModel.signIn(loginDto)

                        if (resultRequest != null) {
                            val jsonResultRequest = JSONObject(resultRequest)
                            if(jsonResultRequest.getJSONObject("error").toString() == "{}"){
                                resultRequestToken = jsonResultRequest.getJSONObject("data").getString("token")

                                //Save token in shared preference
                                if(sharedPreferencesService.retrived("TOKEN", applicationContext) == null) {
                                    sharedPreferencesService.saveIn("TOKEN",
                                        resultRequestToken!!,
                                        applicationContext)
                                }

                                //Stock Informations user in Shared preferences
                                if(sharedPreferencesService.retrivedUser("USER", applicationContext) == null){
                                    val resultUserInformations = connectUserViewModel.getUserInformations(resultRequestToken)
                                    val jsonResultRequest = JSONObject(resultUserInformations)
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
                                    sharedPreferencesService.saveInUser("USER", user, applicationContext)
                                }
                                message = "Connecté"
                            }
                            else{
                                message = jsonResultRequest.getJSONObject("error").getString("code")
                            }
                        }
                        else{
                            message = "Erreur de connexion"

                        }

                    }
                    deferred.await()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

                        /*
                        //To refresh token
                        val resultRefreshToken = connectUserViewModel.refreshTokenUser(token)
                        Log.d("token refresh", resultRefreshToken)
*/

                }
            } else {
                Toast.makeText(this, "Formulaire mal renseigné", Toast.LENGTH_SHORT).show()
            }
        }
    }
//init an account manager
    /*
    val am: AccountManager = AccountManager.get(this) // "this" references the current Context
    val accounts: Array<out Account> = am.getAccountsByType("com.google")
*/
}
