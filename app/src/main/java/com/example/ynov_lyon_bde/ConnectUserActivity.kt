package com.example.ynov_lyon_bde

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.ynov_lyon_bde.api.ApiManager
import com.example.ynov_lyon_bde.model.UserInfo
import kotlinx.android.synthetic.main.activity_connectuser.*

class ConnectUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connectuser)

        //return previous activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonConnect.setOnClickListener {

            // Take informations User
            val email = if (Patterns.EMAIL_ADDRESS.matcher(editTextMail2.text.toString())
                    .matches()
            ) editTextMail2.text.toString() else null
            val password = editTextPassword2.text.toString()

            //send request to api to create user
            if (email != null && password != null) {
                signIn(email, password)
            } else {
                Toast.makeText(this, "Formulaire mal renseigné", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun signIn(email: String, password: String) {
        val apiService = ApiManager()
        val userInfo = UserInfo(  userId = null,
            userFirstName = null,
            userLastName = null,
            userEmail = email,
            userPassword = password,
            userPromotion = null,
            userFormation = null,
            userPictureUrl = null
            )
        apiService.loginUser(userInfo) {
            if (it?.userId != null) {
                // it = connected user parsed as response
                // it?.userId = connected user ID
                Toast.makeText(this, "utilisateur connecté", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erreur lors de la connexion de l'utilisateur", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
