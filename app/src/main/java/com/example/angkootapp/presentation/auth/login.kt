package com.example.angkootapp.presentation.auth

import android.widget.Toast
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
import androidx.compose.ui.text.style.TextAlign
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
    onForgotPasswordClick: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.generalError) {
        viewModel.generalError?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

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
                    text = "Masuk untuk melanjutkan perjalanan",
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
                Spacer(modifier = Modifier.height(60.dp)) // Ruang ikon melayang

                CustomInputField(
                    value = email,
                    onValueChange = { email = it },
                    label = "EMAIL",
                    placeholder = "Masukkan email",
                    leadingIcon = R.drawable.email,
                    isError = viewModel.emailError != null,
                    supportingText = viewModel.emailError
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
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

                    Text(
                        text = "Lupa kata sandi?",
                        color = Color(0xFF2CB9D1),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 8.dp)
                            .clickable { onForgotPasswordClick() }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = "Masuk",
                    onClick = {
                        viewModel.loginEmail(
                            email = email,
                            pass = password,
                            onSuccess = {
                                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                                onLoginSucces()
                            }
                        )
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
                    onTokenReceived = { token, name ->
                        viewModel.loginWithGoogle(idToken = token, onSuccess = { onLoginSucces() })
                    },
                    onError = { }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Tidak punya akun? ")
                        }
                        withStyle(SpanStyle(color = Color(0xFF2CB9D1), fontWeight = FontWeight.Bold)) {
                            append("Daftar")
                        }
                    },
                    modifier = Modifier.clickable { onRegisterClick() },
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

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