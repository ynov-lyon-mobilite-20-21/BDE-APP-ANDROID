package com.example.ynov_lyon_bde.model

import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("id") val userId: Int?,
    @SerializedName("firstName") val userFirstName: String?,
    @SerializedName("lastName") val userLastName: String?,
    @SerializedName("mail") val userEmail: String?,
    @SerializedName("password") val userPassword: String?,
    @SerializedName("promotion") val userPromotion: String?,
    @SerializedName("formation") val userFormation: String?,
    @SerializedName("pictureUrl") val userPictureUrl: String?,
){

    @SerializedName("ResponseCode") var responseCode: String? = null
    @SerializedName("ResponseMessage") var responseMessage: String? = null
}
