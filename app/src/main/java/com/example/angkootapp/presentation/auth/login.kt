package com.example.angkootapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.angkootapp.R
import com.example.angkootapp.model.viewModel.LoginViewModel
import com.example.angkootapp.presentation.components.*

@Composable
fun LoginScreen(
    onBackClick: () -> Unit,
    onLoginSucces: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bgwelcome),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(top = 30.dp, start = 16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Color(0xFFB4B4B4)
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AuthHeader(title = "Masuk")
                CustomInputField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    placeholder = "Masukkan Email",
                    leadingIcon = R.drawable.vector
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomInputField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Kata sandi",
                    placeholder = "Kata sandi",
                    leadingIcon = R.drawable.people,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordToggle = {passwordVisible = !passwordVisible}
                )
                Spacer(modifier = Modifier.height(32.dp))
                PrimaryButton(
                    text = "Masuk",
                    onClick = {
                        viewModel.loginEmail(
                            email = email,
                            pass = password,
                            onSuccess = {
                                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                                onLoginSucces()
                            },
                            onError = { pesan ->
                                Toast.makeText(context, pesan, Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                GoogleSignInButton(
                    onTokenReceived = { token ->
                        viewModel.loginWithGoogle(
                            idToken = token,
                            onSuccess = {
                                Toast.makeText(context, "Selamat Datang!", Toast.LENGTH_SHORT).show()
                                onLoginSucces()
                            },
                            onError = { pesanError ->
                                Toast.makeText(context, "Login Gagal: $pesanError", Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    onError = {
                        Toast.makeText(context, "Google Sign In Dibatalkan", Toast.LENGTH_SHORT).show()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                val annotatedString = buildAnnotatedString {
                    append("Belum Punya Akun? ")
                    pushStringAnnotation(tag = "register", annotation = "register")
                    withStyle(style = SpanStyle(color = Color(0xFF2CB9D1), fontWeight = FontWeight.Bold)) {
                        append("Daftar Sekarang!!")
                    }
                    pop()
                }
                Text(
                    text = annotatedString,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onRegisterClick() }
                )
            }
        }
        if (viewModel.isLoading) {
            LoadingOverlay()
        }
    }
}