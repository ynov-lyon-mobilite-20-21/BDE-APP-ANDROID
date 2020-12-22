package com.example.ynov_lyon_bde.data.model

data class User (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val promotion: String,
    val formation: String,
    val pictureUrl: String // A SUPPRIMER PAR LA SUITE
    )

//    var responseCode: String? = null
//    var responseMessage: String? = null
