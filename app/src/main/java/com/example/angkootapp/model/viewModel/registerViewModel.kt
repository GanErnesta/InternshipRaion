package com.example.angkootapp.model.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.angkootapp.model.data.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    var isLoading by mutableStateOf(false)
        private set

    var nameError by mutableStateOf<String?>(null)
        private set

    var phoneError by mutableStateOf<String?>(null)
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    var generalError by mutableStateOf<String?>(null)
        private set

    fun registerEmail(
        name: String,
        phone: String,
        email: String,
        pass: String,
        onSuccess: () -> Unit
    ) {
        nameError = null
        phoneError = null
        emailError = null
        passwordError = null
        generalError = null

        var hasError = false
        if (name.isBlank()) {
            nameError = "Nama tidak boleh kosong"
            hasError = true
        }

        if (phone.isBlank()) {
            phoneError = "Nomor telepon tidak boleh kosong"
            hasError = true
        } else if (phone.length < 10) {
            phoneError = "Nomor telepon tidak valid"
            hasError = true
        }

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

            result.onSuccess {
                try {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val userMap = hashMapOf(
                            "uid" to uid,
                            "name" to name,
                            "phone" to phone,
                            "email" to email,
                            "balance" to "0K",
                            "tripCount" to 0,
                            "featureCount" to 0,
                            "photoUrl" to ""
                        )

                        db.collection("users").document(uid).set(userMap).await()

                        isLoading = false
                        onSuccess()
                    }
                } catch (e: Exception) {
                    isLoading = false
                    generalError = "Gagal menyimpan ke database: ${e.message}"
                }
            }

            result.onFailure { exception ->
                isLoading = false
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
        nameError = null
        phoneError = null
        emailError = null
        passwordError = null
        generalError = null
    }
}