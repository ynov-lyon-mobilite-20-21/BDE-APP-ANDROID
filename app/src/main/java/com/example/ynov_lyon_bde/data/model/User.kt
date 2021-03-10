package com.example.ynov_lyon_bde.data.model

data class User (
    val id:String,
    val isActive:Boolean,
    val isAdmin:Boolean,
    val isAdherent: Boolean,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val promotion: String,
    val formation: String,
    val activationKey:String
    )
