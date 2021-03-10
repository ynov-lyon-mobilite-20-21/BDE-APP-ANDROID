package com.example.ynov_lyon_bde.domain.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.ResponseBody

class JsonServiceBuilder {

    // Convert raw JSON to pretty JSON using GSON library
    fun convertRawToPrettyJson(response: ResponseBody?): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val gsonPretty = gson.toJson(
            JsonParser.parseString(
                response
                    ?.string()
            )
        )
        return gsonPretty
    }

    fun <T> dataClassObjectToJsonString(dataClassObject : T): String {
        val gson = Gson()
        val jsonTut: String = gson.toJson(dataClassObject)
        return jsonTut
    }

}
