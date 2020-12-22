package com.example.ynov_lyon_bde.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.LoginDTO
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

        val connectUserViewModel = ConnectUserViewModel()
        buttonConnect.setOnClickListener {

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
                    Log.d("TOKEN :", token)
                }
                Toast.makeText(this, "Connecté", Toast.LENGTH_SHORT).show()
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
