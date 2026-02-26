package com.example.angkootapp.model.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.angkootapp.model.data.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    fun registerEmail(email: String, pass: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (email.isBlank() || pass.isBlank()) {
            onError("Email dan password tidak boleh kosong")
            return
        }

        if (pass.length < 8) {
            onError("Password minimal harus 8 karakter")
            return
        }

        viewModelScope.launch {
            isLoading = true
            val result = repository.signUpWithEmail(email, pass)
            isLoading = false

            result.onSuccess { onSuccess() }
            result.onFailure { exception ->
                val errorMessage = when {
                    exception.message?.contains("email-already-in-use") == true -> "Email sudah terdaftar"
                    exception.message?.contains("invalid-email") == true -> "Format email salah"
                    else -> exception.message ?: "Pendaftaran Gagal"
                }
                onError(errorMessage)
            }
        }
    }
}