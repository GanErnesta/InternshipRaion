package com.example.angkootapp.model.data

import com.google.android.gms.maps.model.LatLng

data class TerminalLocation(
    val id: String,
    val name: String,
    val position: LatLng
)