package com.example.angkootapp.model.data

import androidx.compose.ui.graphics.Color

data class TransactionData(
    val title: String,
    val date: String,
    val amount: String,
    val isTopUp: Boolean,
    val status: String
)

data class TopUpUiState(
    val saldo: String = "Rp 0",
    val transactions: List<TransactionData> = emptyList(),
    val isLoading: Boolean = false
)