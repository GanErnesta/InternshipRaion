package com.example.angkootapp.model.interfaceApi

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface MidtransService {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("snap/v1/transactions")
    fun createTransaction(
        @Header("Authorization") auth: String,
        @Body request: Map<String, @JvmSuppressWildcards Any>
    ): Call<Map<String, Any>>
}