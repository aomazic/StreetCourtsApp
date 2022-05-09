package com.ferit.streetcourts

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentId

data class Teren(
    val Adresa: String? = null,
    val BrRating: String? = null,
    val BrojS: String? = null,
    val Ime: String? = null,
    val Javno: Boolean? = null,
    val Skolsko: Boolean? = null,
    val Svlacionice: Boolean? = null,
    val UkRating : String? = null,
    val WC: Boolean? = null,
    val dimenzije: String? = null,
    val lat: String? = null,
    val long: String? = null,
    val podloga: String? = null,
    val rasvjeta: String? = null,
    val slika: String? = null,
    @DocumentId
    val documentId: String = "null"

)

data class  Ratings(
    val ImePrezime: String? = null,
    val RatingStar: String? = null,
    val RatingText: String? = null


)
