package com.example.angkootapp.model.data

import com.google.android.gms.maps.model.LatLng

data class AngkotLocation(
    val id: String,
    val platNomor: String,
    val position: LatLng
)