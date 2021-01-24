package com.example.ynov_lyon_bde.data.repository

import android.content.Context
import android.content.ContextWrapper
import com.example.ynov_lyon_bde.data.model.User
import java.io.File

//TODO à toi de voir si l'utilisation du design pattern repository te semble intéressant, propose une façon de faire :)

class UserRepository(ctx: Context) {

    private val context = ctx
    private val file = File(context.filesDir, "user")

    fun putUserCurrent(user: User){
        val fileContents = "test"
        context.openFileOutput(file.name, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
        /*
        val file:String = fileName
        val data:String = fileData.text.toString()
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
        }catch (e: Exception){
            e.printStackTrace()
        }

         */
    }

    fun getUserCurrent(){
        return context.openFileInput(file.name).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
    }
    //val file = File(context.filesDir, "userFile")

}
