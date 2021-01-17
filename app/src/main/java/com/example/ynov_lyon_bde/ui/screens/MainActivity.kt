package com.example.ynov_lyon_bde.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ynov_lyon_bde.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //to redirect user on activity inscription/connexion/home
        /*
        val redirectService = RedirectService()
        var intent = redirectService.redirect(applicationContext)
        startActivity(intent)

         */
/*
        val user = User(
            id = "id",
            isActive = true,
            isAdmin = true,
            isAdherent = true,
            firstName = "firstName",
            lastName = "lastName",
            mail = "mail",
            promotion = "promotion",
            formation = "formation",
            activationKey = "activationKey"
        )

        val userRepository = UserRepository(applicationContext)
        userRepository.putUserCurrent(user)
        val content = userRepository.getUserCurrent()
        Log.d("content", content.toString())

 */
    }


}
