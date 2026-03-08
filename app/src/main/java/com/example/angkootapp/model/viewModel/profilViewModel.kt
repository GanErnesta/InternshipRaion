package com.example.angkootapp.model.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class ProfileUiState(
    val name: String = "",
    val phone: String = "",
    val photoUrl: String = "",
    val walletBalance: String = "0K",
    val tripCount: Int = 0,
    val featureCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)


class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {

                _uiState.update {
                    it.copy(
                        name          = "Prabowo Subianto",
                        phone         = "08213456789",
                        photoUrl      = "",
                        walletBalance = "150K",
                        tripCount     = 32,
                        featureCount  = 32,
                        isLoading     = false
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error     = e.message
                    )
                }
            }
        }
    }
}