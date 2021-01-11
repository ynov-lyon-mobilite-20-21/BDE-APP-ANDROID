package com.example.ynov_lyon_bde.ui.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.User
import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.data.repository.UserRepository
import com.example.ynov_lyon_bde.domain.services.RedirectService
import kotlinx.coroutines.NonCancellable.start

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val redirectService = RedirectService()

        var intent = redirectService.redirect(applicationContext)
        startActivity(intent)
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
