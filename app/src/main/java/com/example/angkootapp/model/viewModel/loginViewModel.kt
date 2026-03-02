package com.example.angkootapp.model.viewModel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.angkootapp.model.data.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
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

    fun loginEmail(email: String, pass: String, onSuccess: () -> Unit) {
        emailError = null
        passwordError = null
        generalError = null

        var hasError = false

        if (email.isBlank()) {
            emailError = "Email tidak boleh kosong"
            hasError = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Format email tidak valid"
            hasError = true
        }

        if (pass.isBlank()) {
            passwordError = "Password tidak boleh kosong"
            hasError = true
        } else if (pass.length < 8) {
            passwordError = "Password minimal 8 karakter"
            hasError = true
        }

        if (hasError) return

        viewModelScope.launch {
            isLoading = true
            val result = repository.loginWithEmail(email, pass)
            isLoading = false

            result.onSuccess {
                onSuccess()
            }
            result.onFailure { exception ->
                val message = exception.message ?: ""
                when {
                    message.contains("user-not-found") || message.contains("invalid-credential") -> {
                        generalError = "Email atau password salah"
                    }
                    message.contains("invalid-email") -> {
                        emailError = "Format email salah"
                    }
                    message.contains("network-request-failed") -> {
                        generalError = "Koneksi internet bermasalah"
                    }
                    else -> {
                        generalError = message.ifBlank { "Login Gagal" }
                    }
                }
            }
        }
    }

    fun loginWithGoogle(idToken: String, onSuccess: () -> Unit) {
        generalError = null
        viewModelScope.launch {
            isLoading = true
            val result = repository.signInWithGoogle(idToken)
            isLoading = false

            result.onSuccess { onSuccess() }
            result.onFailure {
                generalError = it.message ?: "Gagal login dengan Google"
            }
        }
    }

    fun clearErrors() {
        emailError = null
        passwordError = null
        generalError = null
    }
}