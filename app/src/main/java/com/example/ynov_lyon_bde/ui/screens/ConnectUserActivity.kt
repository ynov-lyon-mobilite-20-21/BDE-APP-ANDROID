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
                var token : String? = null
                val loginDto = LoginDTO(
                    mail = email,
                    password = password
                )
                GlobalScope.launch(Dispatchers.Main) {
                    val deferred = async(Dispatchers.IO) {
                        val resultRequest = connectUserViewModel.signIn(loginDto)

                        if (resultRequest != null) {
                            val jsonResultRequest = JSONObject(resultRequest)
                            token = jsonResultRequest.getJSONObject("data").getString("token")
                        }
                    }
                    deferred.await()

                    if(token !=null){
                        //Save token in shared preference
                        sharedPreferencesService.saveIn("TOKEN", token!!, applicationContext)

                        //Stock Informations user in Shared preferences
                        if(sharedPreferencesService.retrivedUser("USER", applicationContext) == null){
                            val resultUserInformations = connectUserViewModel.getUserInformations(token)
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
                        /*
                        //To refresh token
                        val resultRefreshToken = connectUserViewModel.refreshTokenUser(token)
                        Log.d("token refresh", resultRefreshToken)
*/

                        Toast.makeText(applicationContext, "Connecté", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(applicationContext, "Connexion échouée", Toast.LENGTH_SHORT).show()
                    }
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
