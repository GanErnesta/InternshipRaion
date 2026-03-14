package com.example.angkootapp.model.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.angkootapp.model.interfaceApi.MidtransService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class midtransViewModel : ViewModel() {

    fun bayarAngkot(context: Context, nominal: Int, passengerCount: Int) {
        val serverKey = "SB-Mid-server-JoMSYL5Ia0BlZBasE4dIjth4"
        val auth = "Basic " + Base64.encodeToString("$serverKey:".toByteArray(), Base64.NO_WRAP).trim()

        val requestBody: Map<String, @JvmSuppressWildcards Any> = mapOf(
            "transaction_details" to mapOf(
                "order_id" to "TRX-ANGKOOT-${System.currentTimeMillis()}",
                "gross_amount" to nominal
            ),
            "item_details" to listOf(
                mapOf(
                    "id" to "ID-001",
                    "price" to nominal,
                    "quantity" to 1,
                    "name" to "Tiket Angkot - ${passengerCount} Orang" // Ini akan muncul di detail pembayaran
                )
            ),
            "payment_type" to "qris"
        )

        val retrofit = Retrofit.Builder()
            .baseUrl("https://app.sandbox.midtrans.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MidtransService::class.java)

        service.createTransaction(auth, requestBody).enqueue(object : retrofit2.Callback<Map<String, Any>> {
            override fun onResponse(call: retrofit2.Call<Map<String, Any>>, response: retrofit2.Response<Map<String, Any>>) {
                if (response.isSuccessful && response.body() != null) {
                    val redirectUrl = response.body()?.get("redirect_url") as? String
                    if (!redirectUrl.isNullOrEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redirectUrl)).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Log.e("Midtrans", "Gagal buka browser: ${e.message}")
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Midtrans", "Ditolak Server: $errorBody")
                }
            }

            override fun onFailure(call: retrofit2.Call<Map<String, Any>>, t: Throwable) {
                Log.e("MidtransError", "Gagal koneksi: ${t.message}")
            }
        })
    }
}