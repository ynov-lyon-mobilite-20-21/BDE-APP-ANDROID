package com.example.ynov_lyon_bde.domain.services.request

import org.json.JSONObject
import java.lang.Exception

class ErrorManager {

    enum class ErrorType {
        CODE, ERROR
    }

    //Throw an error if json response of request is null or code request no enter 200 and 299
    fun handleException(apiResponse: String?, errorMessage: ErrorType): Boolean {
        var success: Boolean = true
        if (!apiResponse.isNullOrEmpty()) {
            val t = apiResponse.split(";")
            val code = t[0].toInt()
            val json = t[1]
            val jsonObject = JSONObject(t[1])

            if (json.isEmpty()) {
                throw Exception("Le Json récupéré est null")
            }

            if (code !in 200..299) {
                when (errorMessage) {
                    ErrorType.CODE ->
                        throw Exception(jsonObject.getString("code"))
                    ErrorType.ERROR ->
                        throw Exception(jsonObject.getJSONObject("error").getString("code"))
                }
                success = false
            }

        } else {
            throw Exception("la reponse reçue est null")
            success = false
        }
        return success
    }
}
