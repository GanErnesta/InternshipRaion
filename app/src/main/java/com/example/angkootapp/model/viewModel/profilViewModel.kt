package com.example.angkootapp.model.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// State untuk membungkus semua data Profil
data class ProfileUiState(
    val name: String = "",
    val email: String = "",
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

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    init {
        loadProfile()
    }

    // 1. Fungsi Ambil Data (Sinkronisasi)
    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    // Ambil dokumen dari koleksi "users" berdasarkan UID
                    val document = db.collection("users").document(currentUser.uid).get().await()

                    if (document.exists()) {
                        _uiState.update {
                            it.copy(
                                name = document.getString("name") ?: "User Angkoot",
                                email = currentUser.email ?: "",
                                phone = document.getString("phone") ?: "-",
                                photoUrl = document.getString("photoUrl") ?: "",
                                walletBalance = document.getString("balance") ?: "0K",
                                tripCount = document.getLong("tripCount")?.toInt() ?: 0,
                                featureCount = document.getLong("featureCount")?.toInt() ?: 0,
                                isLoading = false
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                name = currentUser.displayName ?: "User Angkoot",
                                email = currentUser.email ?: "",
                                isLoading = false
                            )
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false, error = "Sesi berakhir. Silakan login kembali.")
                    }
                }
            } catch (e: Exception) {
                val msg = if (e.message?.contains("offline") == true) {
                    "Tidak ada koneksi internet."
                } else {
                    e.message ?: "Gagal memuat profil"
                }
                _uiState.update { it.copy(isLoading = false, error = msg) }
            }
        }
    }

    fun updateProfile(newName: String, newPhone: String, onSelection: (Boolean) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val uid = auth.currentUser?.uid
                if (uid != null) {
                    val updates = mapOf(
                        "name" to newName,
                        "phone" to newPhone
                    )
                    db.collection("users").document(uid).update(updates).await()

                    // Panggil lagi loadProfile agar UI langsung berubah
                    loadProfile()
                    onSelection(true)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
                onSelection(false)
            }
        }
    }

    // 3. Fungsi Logout
    fun logout() {
        auth.signOut()
        _uiState.update { ProfileUiState() } // Reset state saat logout
    }
}