package com.example.ynov_lyon_bde.data.model

data class UserDTO(
    val firstName: String,
    val lastName: String,
    val mail: String,
    val password: String,
    val promotion: String,
    val formation: String,
    val pictureUrl: String // A SUPPRIMER PAR LA SUITE
)
