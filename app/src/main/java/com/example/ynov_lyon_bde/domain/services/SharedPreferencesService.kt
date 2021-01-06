package com.example.ynov_lyon_bde.domain.services

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.ynov_lyon_bde.data.model.User
import com.google.gson.Gson

class SharedPreferencesService {

    fun saveIn(nameReference : String, value : String, context : Context){
        val preferences: SharedPreferences = context.getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        preferences.edit().putString(nameReference, value).apply()
    }

    fun retrived(nameReference: String, context: Context): String? {
        val preferences: SharedPreferences = context.getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        return preferences.getString(nameReference, null) //second parameter default value.
    }

}
