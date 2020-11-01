
package com.example.ynov_lyon_bde

import android.accounts.Account
import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_createuser.*

class CreateUserActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createuser)

        buttonValidate.setOnClickListener {
            // Take informations User
            val email = editTextMail.text
            val psswd = editTextPassword.text
            val phone = editTextPhone.text
            val classe = editTextClass.text
            val faculty = editTextFaculty.text


// Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            //val url = "https://self-buy-api.herokuapp.com/api/users"
            val url = "https://www.google.fr/"
// Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    // Display the response string.
                    println("Response is: $response")
                },
                { println("That didn't work!") })

// Add the request to the RequestQueue.
            queue.add(stringRequest)

            //account manager
            val am: AccountManager = AccountManager.get(this) // "this" references the current Context

            val accounts: Array<out Account> = am.getAccountsByType("com.google")
        }

    }


}

