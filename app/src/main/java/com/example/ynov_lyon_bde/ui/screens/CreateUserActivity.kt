package com.example.ynov_lyon_bde.ui.screens


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.domain.services.SharedPreferencesService
import com.example.ynov_lyon_bde.domain.viewmodel.ConnectUserViewModel
import com.example.ynov_lyon_bde.domain.viewmodel.CreateUserViewModel
import kotlinx.android.synthetic.main.activity_createuser.*
import kotlinx.coroutines.*
import org.json.JSONObject


class CreateUserActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createuser)

        //return previous activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Spinner to take class for a new user
        val spinnerP: Spinner = findViewById(R.id.spinnerPromotion)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.promotion_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerP.adapter = adapter
        }

        //Spinner to take faculty for a new user
        val spinnerF: Spinner = findViewById(R.id.spinnerFormation)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.formation_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerF.adapter = adapter
        }

        buttonCreateUser.setOnClickListener {

            //access to view model and service
            val sharedPreferencesService = SharedPreferencesService()
            val createUserViewModel = CreateUserViewModel()
            val connectUserViewModel = ConnectUserViewModel()

            // Take informations User
            val email =
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(editTextMail.text.toString())
                        .matches()
                ) editTextMail.text.toString() else null
            var firstName: String? = null
            var lastName: String? = null
            if (email != null) {
                val names: String = editTextMail.text.toString().split('@')[0]
                firstName = names.split('.')[0]
                lastName = names.split('.')[1]
            }

            val password = editTextPassword.text.toString()
            val promotion = spinnerP.getSelectedItem().toString()
            val formation = spinnerF.getSelectedItem().toString()

            if (firstName != null && lastName != null && email != null && password != "" && promotion != "" && formation != "") {
                var message: String? = null
                var resultRequestToken: String?

                //create DTO models
                val userDto = UserDTO(
                    firstName = firstName,
                    lastName = lastName,
                    mail = email,
                    password = password,
                    promotion = promotion,
                    formation = formation
                )
                val loginDto = LoginDTO(
                    mail = email,
                    password = password
                )

                //send request to api to create user
                GlobalScope.launch(Dispatchers.Main) {
                    val deferred = async(Dispatchers.IO) {

                        //Register request
                        val resultRequestCreateUser =
                            createUserViewModel.signUp(userDto) // [0]code response [1]json response
                        if (resultRequestCreateUser != null) {
                            val jsonResultRqRegister = JSONObject(resultRequestCreateUser[1])
                            if (resultRequestCreateUser[0].toInt() in 200..299) {
                                message =
                                    jsonResultRqRegister.getJSONObject("data").getString("message")

                                //Login request
                                val resultRequestLogin = connectUserViewModel.signIn(loginDto)
                                if (resultRequestLogin != null) {
                                    val jsonResultRqLogin = JSONObject(resultRequestLogin[1])
                                    if (resultRequestLogin[0].toInt() in 200..299) {
                                        resultRequestToken = jsonResultRqLogin.getJSONObject("data")
                                            .getString("token")

                                        //Save token in shared preference
                                        if (sharedPreferencesService.retrived("TOKEN",
                                                applicationContext) == null
                                        ) {
                                            sharedPreferencesService.saveIn("TOKEN",
                                                resultRequestToken!!,
                                                applicationContext)
                                        }

                                        //Information user request
                                        val resultUserInformations =
                                            connectUserViewModel.getUserInformations(
                                                resultRequestToken)
                                        if (resultUserInformations != null) {
                                            val jsonResultRqInfo =
                                                JSONObject(resultUserInformations[1])

                                            if (resultUserInformations[0].toInt() in 200..299) {
                                                //TODO : create repository for store user
                                                //TODO : go to home activity
                                            } else {
                                                message = jsonResultRqInfo.getString("code")
                                            }
                                        } else {
                                            message = "Erreur de récupération des informations"
                                        }

                                    } else {
                                        message = jsonResultRqLogin.getJSONObject("error")
                                            .getString("code")
                                    }
                                } else {
                                    message = "Erreur de connexion"
                                }
                            } else {
                                message =
                                    jsonResultRqRegister.getJSONObject("error").getString("code")
                            }
                        } else {
                            message = "Erreur d'inscription"
                        }

                    }
                    deferred.await()
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Formulaire mal renseigné", Toast.LENGTH_SHORT).show()
            }

        }
    }
}




