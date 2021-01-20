package com.example.ynov_lyon_bde.ui.screens


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.domain.utils.SpinnerService
import com.example.ynov_lyon_bde.domain.viewmodel.CreateUserViewModel
import kotlinx.android.synthetic.main.fragment_createuser.*
import kotlinx.android.synthetic.main.fragment_third_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class SignUpFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_createuser, container, false)

        val createUserViewModel = CreateUserViewModel()
        val spinnerAdapter = SpinnerService()

//        //Init spinner Promotion
//        val spinnerP: Spinner = spinnerPromotion
//        val adapterspinnerP = context?.let {
//            spinnerAdapter.initAdapter(
//                it,
//                ArrayList<String>(listOf(*resources.getStringArray(R.array.promotion_array))),
//                spinnerP)
//        }
//        spinnerP.adapter = adapterspinnerP
//
//        //Init spinner formation
//        val spinnerF: Spinner = spinnerFormation
//        val adapterspinnerF = context?.let {
//            spinnerAdapter.initAdapter(
//                it,
//                ArrayList<String>(listOf(*resources.getStringArray(R.array.formation_array))),
//                spinnerF)
//        }
//        spinnerF.adapter = adapterspinnerF
//
//        //Show / Hide button
//        showHideButton.setOnClickListener {
//            if(editTextPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
//                editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
//                //TODO set an icon hide
//                showHideButton.setImageResource(R.drawable.hide)
//            } else{
//                editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
//                //TODO set an icon show
//                showHideButton.setImageResource(R.drawable.show)
//            }
//        }
//
//        buttonCreateUser.setOnClickListener {
//
//            // Take informations User
//            val email =
//                if (android.util.Patterns.EMAIL_ADDRESS.matcher(editTextMail.text.toString())
//                        .matches()
//                ) editTextMail.text.toString() else null
//            var firstName: String? = null
//            var lastName: String? = null
//            if (email != null) {
//                val names: String = editTextMail.text.toString().split('@')[0]
//                firstName = names.split('.')[0]
//                lastName = names.split('.')[1]
//            }
//
//            val password = editTextPassword.text.toString()
//            val promotion = spinnerP.getSelectedItem().toString()
//            val formation = spinnerF.getSelectedItem().toString()
//
//            if (firstName != null && lastName != null && email != null && password != "" && promotion != "" && formation != "") {
//                var message: String? = null
//
//
//                //create DTO models
//                val userDto = UserDTO(
//                    firstName = firstName,
//                    lastName = lastName,
//                    mail = email,
//                    password = password,
//                    promotion = promotion,
//                    formation = formation
//                )
//                val loginDto = LoginDTO(
//                    mail = email,
//                    password = password
//                )
//
//                //send request to api to create user
//                GlobalScope.launch(Dispatchers.Main) {
//                    val deferred = async(Dispatchers.IO) {
//                        //call requests
//                        try {
//                            context?.let { it1 ->
//                                createUserViewModel.callApi(userDto, loginDto,
//                                    it1
//                                )
//                            }
//                        } catch (err: Exception) {
//                            message = err.message
//                            Log.e("message", message)
//                        }
//                    }
//                    deferred.await()
//                    if (message.isNullOrEmpty()) {
//                        activity?.finish()
//                    } else {
//                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } else {
//                Toast.makeText(context, "Formulaire mal renseigné", Toast.LENGTH_SHORT).show()
//            }
//
//        }
        return view

    }
}




