package com.example.rently.model.google

data class GoogleLocationResponse(
    val results: ArrayList<GoogleLocationResult>
){
    data class GoogleLocationResult(
        val geometry: GoogleLocationGeometry
    ){
        data class GoogleLocationGeometry(
            val location: GoogleLocation,
        ){
            data class GoogleLocation(
                val lat: Number,
                val lng: Number
            )
        }
    }
}
