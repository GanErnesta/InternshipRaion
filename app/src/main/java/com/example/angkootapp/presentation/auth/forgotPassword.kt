package com.example.angkootapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkootapp.R
import com.example.angkootapp.presentation.components.CustomInputField
import com.example.angkootapp.presentation.components.PrimaryButton
import java.net.URLDecoder

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onNavigateToReset: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // State untuk AlertDialog
    var showDialog by remember { mutableStateOf(false) }
    var verificationInput by remember { mutableStateOf("") }

    // --- LOGIKA DIALOG ---
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Verifikasi Link", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        text = "Silakan buka email dan tempel link reset password yang dikirimkan di bawah ini.",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = verificationInput,
                        onValueChange = { verificationInput = it },
                        label = { Text("Tempel Link atau Kode") },
                        placeholder = { Text("https://angkootapp.firebaseapp.com/...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                    Text(
                        text = "Tips: Cukup salin seluruh alamat link dari email.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (verificationInput.isNotBlank()) {
                            // Proses pengambilan kode yang bersih
                            val cleanCode = getOobCodeFromUrl(verificationInput)
                            showDialog = false
                            onNavigateToReset(cleanCode)
                        } else {
                            Toast.makeText(context, "Input tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("LANJUT", color = Color(0xFF268696), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("BATAL", color = Color.Gray)
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
        // 1. Header Biru
        Surface(
            modifier = Modifier.fillMaxWidth().height(280.dp),
            color = Color(0xFF268696),
            shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 60.dp)
            ) {
                Text("Lupa kata sandi", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Isi form dibawah untuk reset kata sandi", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
            }
        }

        // 2. Tombol Back
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(top = 40.dp, start = 16.dp)
        ) {
            Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.2f), modifier = Modifier.size(40.dp)) {
                Icon(Icons.Default.ArrowBackIosNew, "Back", tint = Color.White, modifier = Modifier.padding(8.dp))
            }
        }

        // 3. Card Putih
        Surface(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(top = 220.dp),
            shape = RoundedCornerShape(32.dp), color = Color.White, shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp).verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                CustomInputField(
                    value = email,
                    onValueChange = { email = it },
                    label = "EMAIL",
                    placeholder = "Masukkan email",
                    leadingIcon = R.drawable.email,
                )

                Spacer(modifier = Modifier.height(32.dp))

                PrimaryButton(
                    text = "Konfirmasi",
                    isLoading = isLoading,
                    onClick = {
                        if (email.isNotEmpty()) {
                            isLoading = true
                            auth.sendPasswordResetEmail(email)
                                .addOnCompleteListener { task ->
                                    isLoading = false
                                    if (task.isSuccessful) {
                                        Toast.makeText(context, "Email terkirim!", Toast.LENGTH_SHORT).show()
                                        showDialog = true
                                    } else {
                                        Toast.makeText(context, "Gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(context, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // 4. Floating Icon
        Surface(
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 180.dp).size(80.dp),
            shape = CircleShape, color = Color.White, shadowElevation = 4.dp, border = BorderStroke(4.dp, Color(0xFFE0F2F1))
        ) {
            Icon(painterResource(R.drawable.people), null, tint = Color(0xFF268696), modifier = Modifier.padding(16.dp))
        }
    }
}

/**
 * Fungsi ini menangani pembersihan link Firebase.
 * Mendukung input berupa link utuh maupun kode mentah.
 */
fun getOobCodeFromUrl(input: String): String {
    return try {
        val trimmed = input.trim()
        if (trimmed.contains("oobCode=")) {
            // Ambil bagian setelah oobCode= dan sebelum tanda & (jika ada)
            val codePart = trimmed.substringAfter("oobCode=").substringBefore("&")
            // Decode karakter spesial seperti %3D menjadi =
            URLDecoder.decode(codePart, "UTF-8")
        } else {
            trimmed // Jika user memasukkan kode mentah langsung
        }
    } catch (e: Exception) {
        input.trim()
    }
}