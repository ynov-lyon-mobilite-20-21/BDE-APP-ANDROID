
package com.example.ynov_lyon_bde


import android.accounts.Account
import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ynov_lyon_bde.api.ApiManager
import com.example.ynov_lyon_bde.model.UserInfo
import kotlinx.android.synthetic.main.activity_createuser.*



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

        val listParameters = arrayListOf<String>()

        buttonCreateUser.setOnClickListener {
            // Take informations User
            val email = if(android.util.Patterns.EMAIL_ADDRESS.matcher(editTextMail.text.toString()).matches())  editTextMail.text.toString() else null
            var firstName: String? = null
            var lastName: String? = null
            if(email != null){
                val names: String = editTextMail.text.toString().split('@')[0]
                firstName = names.split('.')[0]
                lastName = names.split('.')[1]
            }

            val password = editTextPassword.text.toString()
            val promotion = spinnerP.getSelectedItem().toString()
            val formation = spinnerF.getSelectedItem().toString()
            val pictureUrl = "imageeeee"

            println("//////////////////"+ firstName+lastName+email+password+promotion+formation)
            //send request to api to create user
            if(firstName!= null && lastName!= null && email!= null && password!= null && promotion!= null && formation!= null){
                signUp(firstName,lastName,email,password,promotion,formation,pictureUrl)
            }
            else{
                Toast.makeText(this, "Formulaire mal renseigné", Toast.LENGTH_SHORT).show()
            }


            //init an account manager
            /*
            val am: AccountManager = AccountManager.get(this) // "this" references the current Context
            val accounts: Array<out Account> = am.getAccountsByType("com.google")
*/
        }


    }

    private fun signUp(firstName: String, lastName: String, email: String, password: String, promotion: String, formation: String, pictureUrl:String) {
        val apiService = ApiManager()
        val userInfo = UserInfo(  userId = null,
            userFirstName = firstName,
            userLastName = lastName,
            userEmail = email,
            userPassword = password,
            userPromotion = promotion,
            userFormation = formation,
            userPictureUrl = pictureUrl)

        apiService.addUser(userInfo) {
            if (it?.userId != null) {
                // it = newly added user parsed as response
                // it?.userId = newly added user ID
                    println(it.userFirstName)
                Toast.makeText(this, "nouvel utilisateur ajouté", Toast.LENGTH_SHORT).show()
            } else {
                println(it?.userEmail+it?.responseMessage+ it?.responseCode)
                Toast.makeText(this, "Erreur lors de l'enregistrement d'un nouvel utilisateur", Toast.LENGTH_SHORT).show()
            }
        }
    }
}




