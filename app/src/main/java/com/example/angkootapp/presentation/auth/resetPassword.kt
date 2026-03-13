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

@Composable
fun ResetPasswordScreen(
    oobCode: String,
    onBackClick: () -> Unit,
    onResetSuccess: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val auth = com.google.firebase.auth.FirebaseAuth.getInstance()

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
        // 1. Header Biru
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            color = Color(0xFF268696),
            shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 60.dp)
            ) {
                Text(
                    text = "Kata Sandi Baru",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Buat kata sandi baru yang kuat",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        // 2. Tombol Back
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(top = 40.dp, start = 16.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.2f),
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        // 3. Card Putih
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 220.dp, bottom = 20.dp),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                CustomInputField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = "KATA SANDI BARU",
                    placeholder = "Masukkan kata sandi baru",
                    leadingIcon = R.drawable.lock,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordToggle = { passwordVisible = !passwordVisible },
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomInputField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "KONFIRMASI KATA SANDI",
                    placeholder = "Ulangi kata sandi baru",
                    leadingIcon = R.drawable.lock,
                    isPassword = true,
                    passwordVisible = confirmPasswordVisible,
                    onPasswordToggle = { confirmPasswordVisible = !confirmPasswordVisible },
                )

                Spacer(modifier = Modifier.height(32.dp))

                PrimaryButton(
                    text = "Konfirmasi",
                    isLoading = isLoading,
                    onClick = {
                        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                            Toast.makeText(context, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        } else if (newPassword != confirmPassword) {
                            Toast.makeText(context, "Password tidak cocok!", Toast.LENGTH_SHORT).show()
                        } else {
                            isLoading = true
                            auth.confirmPasswordReset(oobCode, newPassword)
                                .addOnCompleteListener { task ->
                                    isLoading = false
                                    if (task.isSuccessful) {
                                        Toast.makeText(context, "Kata sandi berhasil diubah!", Toast.LENGTH_SHORT).show()
                                        onResetSuccess()
                                    } else {
                                        val errorMessage = task.exception?.message ?: "Terjadi kesalahan"
                                        Toast.makeText(context, "Gagal: $errorMessage", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        // 4. Floating Icon
        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 180.dp)
                .size(80.dp),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 4.dp,
            border = BorderStroke(4.dp, Color(0xFFE0F2F1))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.lock),
                contentDescription = null,
                tint = Color(0xFF268696),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}