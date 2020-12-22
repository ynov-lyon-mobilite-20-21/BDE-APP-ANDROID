package com.example.ynov_lyon_bde.domain.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.Response

class JsonService {

    // Convert raw JSON to pretty JSON using GSON library
    fun convertRawToPrettyJson(response: Response<ResponseBody>): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val prettyJson = gson.toJson(
            JsonParser.parseString(
                response.body()
                    ?.string()
            )
        )
        return prettyJson
    }
}
