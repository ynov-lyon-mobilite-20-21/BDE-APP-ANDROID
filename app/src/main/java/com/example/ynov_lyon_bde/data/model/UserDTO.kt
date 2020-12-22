package com.example.ynov_lyon_bde.data.model

import com.google.gson.annotations.SerializedName

data class UserDTO(
    val firstName: String,
    val lastName: String,
    val mail: String,
    val password: String,
    val promotion: String,
    val formation: String,
)

