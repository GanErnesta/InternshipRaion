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

    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    var generalError by mutableStateOf<String?>(null)
        private set

    fun registerEmail(email: String, pass: String, onSuccess: () -> Unit) {
        emailError = null
        passwordError = null
        generalError = null

        var hasError = false

        if (email.isBlank()) {
            emailError = "Email tidak boleh kosong"
            hasError = true
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Format email tidak valid"
            hasError = true
        }

        if (pass.isBlank()) {
            passwordError = "Password tidak boleh kosong"
            hasError = true
        } else if (pass.length < 8) {
            passwordError = "Password minimal harus 8 karakter"
            hasError = true
        }

        if (hasError) return

        viewModelScope.launch {
            isLoading = true
            val result = repository.signUpWithEmail(email, pass)
            isLoading = false

            result.onSuccess {
                onSuccess()
            }
            result.onFailure { exception ->
                val message = exception.message ?: ""
                when {
                    message.contains("email-already-in-use") -> {
                        emailError = "Email sudah terdaftar"
                    }
                    message.contains("invalid-email") -> {
                        emailError = "Format email salah"
                    }
                    message.contains("network-request-failed") -> {
                        generalError = "Koneksi internet bermasalah"
                    }
                    else -> {
                        generalError = message.ifBlank { "Pendaftaran Gagal" }
                    }
                }
            }
        }
    }

    fun clearErrors() {
        emailError = null
        passwordError = null
        generalError = null
    }
}