package com.example.angkootapp.model.viewModel

import androidx.lifecycle.ViewModel
import com.example.angkootapp.model.data.OrderData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class ActivityViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    fun saveOrderToFirestore(
        namaAngkot: String,
        rute: String,
        hargaLabel: String,
        tarif: Int,
        metode: String,
        penumpang: String
    ) {
        // Mengambil waktu saat ini untuk format tanggal dan jam manual
        val currentTimestamp = Timestamp.now()
        val dateSdf = java.text.SimpleDateFormat("dd MMM", java.util.Locale.getDefault())
        val timeSdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())

        val newOrder = OrderData(
            namaAngkot = namaAngkot,
            rute = rute,
            tanggal = dateSdf.format(currentTimestamp.toDate()),
            jam = timeSdf.format(currentTimestamp.toDate()),
            hargaLabel = hargaLabel,
            tarif = tarif,
            metodePembayaran = metode,
            status = "selesai", // Pastikan huruf kecil agar sesuai filter di RiwayatContent
            co2Saved = 0.8, // Nilai default sesuai desainmu
            penumpang = penumpang,
            timestamp = currentTimestamp
        )

        db.collection("orders")
            .add(newOrder)
            .addOnSuccessListener {
                println("Order berhasil disimpan!")
            }
            .addOnFailureListener { e ->
                println("Gagal simpan order: ${e.message}")
            }
    }
}