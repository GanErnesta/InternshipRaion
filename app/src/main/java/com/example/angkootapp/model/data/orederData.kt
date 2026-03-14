package com.example.angkootapp.model.data

import androidx.annotation.Keep
import com.google.firebase.Timestamp

@Keep
data class OrderData(
    val namaAngkot: String = "",
    val rute: String = "",
    val tanggal: String = "",
    val jam: String = "",
    val hargaLabel: String = "",
    val tarif: Int = 0,
    val metodePembayaran: String = "",
    val status: String = "",
    val co2Saved: Double = 0.0,
    val penumpang: String = "",
    val timestamp: Timestamp? = null // Gunakan tipe Timestamp dari Firebase
)