package com.example.ynov_lyon_bde.ui.screens


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.domain.utils.SpinnerService
import com.example.ynov_lyon_bde.domain.viewmodel.CreateUserViewModel
import kotlinx.android.synthetic.main.activity_createuser.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class CreateUserActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createuser)


        //return previous activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val createUserViewModel = CreateUserViewModel()
        val spinnerAdapter = SpinnerService()

        // list of spinner Formation
        val listFormation = ArrayList<String>(listOf(*resources.getStringArray(R.array.formation_array)))
        val listPromotion = ArrayList<String>(listOf(*resources.getStringArray(R.array.promotion_array)))


        //Init spinner Promotion
        val spinnerP: Spinner = findViewById(R.id.spinnerPromotion)
        val adapterspinnerP = spinnerAdapter.initAdapter(applicationContext,
            listPromotion,
            spinnerP)
        spinnerP.adapter = adapterspinnerP

        //Init spinner formation
        val spinnerF: Spinner = findViewById(R.id.spinnerFormation)
        val adapterspinnerF = spinnerAdapter.initAdapter(applicationContext,
            listFormation,
            spinnerF)
        spinnerF.adapter = adapterspinnerF

        //Show / Hide button
        showHideButton.setOnClickListener {
            if(editTextPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                //TODO set an icon hide
            } else{
                editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                //TODO set an icon show
            }
        }


        buttonCreateUser.setOnClickListener {

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
                        //call requests
                        try {
                            createUserViewModel.callApi(userDto, loginDto, applicationContext)
                        } catch (err: Exception) {
                            message = err.message
                            Log.e("message", message)
                        }
                    }
                    deferred.await()
                    if (message.isNullOrEmpty()) {
                        val intent = Intent().setClass(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        //TODO : change to home activity
                    } else {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Formulaire mal renseigné", Toast.LENGTH_SHORT).show()
            }

        }
    }

}




