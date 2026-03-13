package com.example.angkootapp.model.utils

import com.google.android.gms.maps.model.LatLng

object MapUtils {
    fun interpolateLatLng(fraction: Float, from: LatLng, to: LatLng): LatLng {
        val lat = (to.latitude - from.latitude) * fraction + from.latitude
        val lng = (to.longitude - from.longitude) * fraction + from.longitude
        return LatLng(lat, lng)
    }
    fun calculateRotation(from: LatLng, to: LatLng): Float {
        val deltaLat = to.latitude - from.latitude
        val deltaLng = to.longitude - from.longitude
        return (Math.toDegrees(Math.atan2(deltaLng, deltaLat)).toFloat())
    }
}