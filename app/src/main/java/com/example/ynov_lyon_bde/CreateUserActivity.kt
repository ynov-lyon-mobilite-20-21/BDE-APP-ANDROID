
package com.example.ynov_lyon_bde

import android.accounts.Account
import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_createuser.*

class CreateUserActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createuser)

        //return previous activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //Spinner to take class for a new user
        val spinnerC: Spinner = findViewById(R.id.spinnerClasse)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.classe_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerC.adapter = adapter
        }

        //Spinner to take faculty for a new user
        val spinnerF: Spinner = findViewById(R.id.spinnerFaculty)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.faculty_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerF.adapter = adapter
        }

        //Spinner to take statut for a new user
        val spinnerS: Spinner = findViewById(R.id.spinnerStatut)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.statut_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerF.adapter = adapter
        }

        buttonValidate.setOnClickListener {
            // Take informations User
            val email = editTextMail.text
            val psswd = editTextPassword.text
            val classe = spinnerC.getSelectedItem().toString()
            val faculty = spinnerF.getSelectedItem().toString()
            val statut = spinnerS.getSelectedItem().toString()

            /*******SEND REQUEST TO API AND TAKE RESPONSE***********/
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



            //init an account manager
            val am: AccountManager = AccountManager.get(this) // "this" references the current Context
            val accounts: Array<out Account> = am.getAccountsByType("com.google")

        }

        fun signUp(email: String?, password: String?): String? {
            // TODO: if user don't exist, register new user on the server and return its auth token
            return null
        }
    }


}

