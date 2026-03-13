package com.example.angkootapp.model.viewModel

import androidx.lifecycle.ViewModel
import com.example.angkootapp.model.data.TopUpUiState
import com.example.angkootapp.model.data.TransactionData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TopUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TopUpUiState())
    val uiState: StateFlow<TopUpUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.update { it.copy(isLoading = true) }

        val dummyList = listOf(
            TransactionData("Top Up Saldo", "12 Okt 2023, 14:20", "+Rp 20.000", true, "Berhasil"),
            TransactionData("Bayar Angkot ADL", "11 Okt 2023, 08:45", "-Rp 5.000", false, "Selesai"),
            TransactionData("Top Up Saldo", "10 Okt 2023, 19:10", "+Rp 50.000", true, "Berhasil")
        )

        _uiState.update {
            it.copy(
                saldo = "Rp 25.000",
                transactions = dummyList,
                isLoading = false
            )
        }
    }
}