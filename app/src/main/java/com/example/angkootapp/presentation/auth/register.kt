package com.example.angkootapp.presentation.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.angkootapp.R
import com.example.angkootapp.model.viewModel.RegisterViewModel
import com.example.angkootapp.presentation.components.*

@Composable
fun RegisterScreen(
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmpasswordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
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
                    text = "Selamat Datang!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Daftar untuk membuat akun",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(top = 40.dp, start = 16.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White.copy(0.2f),
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
                Spacer(modifier = Modifier.height(60.dp)) // Ruang untuk icon profil melayang
                CustomInputField(
                    value = name,
                    onValueChange = { name = it },
                    label = "NAMA LENGKAP",
                    placeholder = "Masukkan nama lengkap",
                    leadingIcon = R.drawable.people,
                    isError = viewModel.nameError != null,
                    supportingText = viewModel.nameError
                )
                CustomInputField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = "NO TELP",
                    placeholder = "Masukkan no telp",
                    leadingIcon = R.drawable.telp, // Pastikan ada ic phone
                    keyboardType = KeyboardType.Number,
                    isError = viewModel.phoneError != null,
                    supportingText = viewModel.phoneError
                )

                CustomInputField(
                    value = email,
                    onValueChange = { email = it },
                    label = "EMAIL",
                    placeholder = "Masukkan email",
                    leadingIcon = R.drawable.email,
                    isError = viewModel.emailError != null,
                    supportingText = viewModel.emailError
                )
                CustomInputField(
                    value = password,
                    onValueChange = { password = it },
                    label = "KATA SANDI",
                    placeholder = "Masukkan kata sandi",
                    leadingIcon = R.drawable.lock,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordToggle = { passwordVisible = !passwordVisible },
                    isError = viewModel.passwordError != null,
                    supportingText = viewModel.passwordError
                )
                CustomInputField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "KONFIRMASI KATA SANDI",
                    placeholder = "Masukkan kata sandi",
                    leadingIcon = R.drawable.lock,
                    isPassword = true,
                    passwordVisible = confirmpasswordVisible,
                    onPasswordToggle = { confirmpasswordVisible = !confirmpasswordVisible }
                )
                Spacer(modifier = Modifier.height(24.dp))
                PrimaryButton(
                    text = "Daftar",
                    onClick = {
                        viewModel.registerEmail(name, phone, email, password, onSuccess = onRegisterSuccess)
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
                    Text(" Atau ", color = Color.Gray, fontSize = 12.sp)
                    Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
                }
                Spacer(modifier = Modifier.height(24.dp))
                GoogleSignInButton(
                    onTokenReceived = { token, name -> },
                    onError = { }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = buildAnnotatedString {
                        append("Sudah punya akun? ")
                        withStyle(SpanStyle(color = Color(0xFF2CB9D1), fontWeight = FontWeight.Bold)) {
                            append("Masuk")
                        }
                    },
                    modifier = Modifier.clickable { onLoginClick() },
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 180.dp) // Posisi tepat di perbatasan header dan card
                .size(80.dp),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 4.dp,
            border = BorderStroke(4.dp, Color(0xFFE0F2F1))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.people),
                contentDescription = null,
                tint = Color(0xFF268696),
                modifier = Modifier.padding(16.dp)
            )
        }
        if (viewModel.isLoading) {
            LoadingOverlay()
        }
    }
}