package com.example.ynov_lyon_bde.model

data class UserDTO(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val promotion: String,
    val formation: String,
    val pictureUrl: String
)
