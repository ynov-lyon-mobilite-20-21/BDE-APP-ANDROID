package com.example.ynov_lyon_bde.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    var id: String,
    var name: String,
    var date: String,
    var type: EventType,
    var image: ImageType,
    var address: String,
    var description: String,
    var price: Int
): Parcelable

enum class EventType(var eventType: String) {
    DEFAULTVALUE(""),
    KOLOK("Call Kolok"),
    STUDENTPARTY("Soirée Etudiante"),
    LAN("LAN"),
    SPORTPARTY("Un moment sportif"),
    FOODSELLING("Vente de nourriture")

}

enum class ImageType(var imageType: String) {
    DEFAULTVALUE(""),
    KOLOK("kolok_card"),
    PARTY("party_card"),
    LAN("lan_card"),
    SPORT("sport_card"),
    FOOD("food_card")
}

