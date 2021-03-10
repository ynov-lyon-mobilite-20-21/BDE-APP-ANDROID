package com.example.ynov_lyon_bde.domain.services

import android.content.Context
import android.content.SharedPreferences
import com.example.ynov_lyon_bde.data.model.User
import com.google.gson.Gson

class SharedPreferencesService {

    fun saveIn(nameReference: String, value: String, context: Context){
        val preferences: SharedPreferences = context.getSharedPreferences(
            "MY_APP",
            Context.MODE_PRIVATE
        )
        preferences.edit().putString(nameReference, value).apply()
    }

    fun retrived(nameReference: String, context: Context): String? {
        val preferences: SharedPreferences = context.getSharedPreferences(
            "MY_APP",
            Context.MODE_PRIVATE
        )
        return preferences.getString(nameReference, null) //second parameter default value.
    }

    fun saveInUser(user: User, context: Context){
        val preferences: SharedPreferences = context.getSharedPreferences(
            "MY_APP",
            Context.MODE_PRIVATE
        )
        val prefsEditor: SharedPreferences.Editor = preferences.edit()
        val gson = Gson()
        val json = gson.toJson(user)
        prefsEditor.putString("User", json)
        prefsEditor.commit()
    }

    fun retrivedUser(context:Context): User? {
        val preferences: SharedPreferences = context.getSharedPreferences(
            "MY_APP",
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json: String? = preferences.getString("User", "")
        return gson.fromJson(json, User::class.java)
    }

}
