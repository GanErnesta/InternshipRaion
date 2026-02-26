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

    fun loginWithGoogle(idToken: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            val result = repository.signInWithGoogle(idToken)
            isLoading = false

            result.onSuccess { onSuccess() }
            result.onFailure { onError(it.message ?: "Terjadi kesalahan") }
        }
    }

    fun loginEmail(email: String, pass: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (email.isBlank() || pass.isBlank()) {
            onError("Email dan Password tidak boleh kosong")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onError("Format email tidak valid")
            return
        }
        if (pass.length < 8) {
            onError("Password minimal 8 karakter")
            return
        }

        viewModelScope.launch {
            isLoading = true
            val result = repository.loginWithEmail(email, pass)
            isLoading = false

            result.onSuccess { onSuccess() }
            result.onFailure { exception ->
                val errorMessage = when {
                    exception.message?.contains("user-not-found") == true -> "Email belum terdaftar"
                    exception.message?.contains("wrong-password") == true -> "Password salah"
                    exception.message?.contains("invalid-email") == true -> "Format email salah"
                    else -> exception.message ?: "Login Gagal"
                }
                onError(errorMessage)
            }
        }
    }
}