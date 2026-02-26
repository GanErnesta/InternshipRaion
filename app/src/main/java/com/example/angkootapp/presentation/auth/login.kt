package com.example.angkootapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.angkootapp.R
import com.example.angkootapp.model.viewModel.LoginViewModel
import com.example.angkootapp.presentation.components.CustomInputField
import com.example.angkootapp.presentation.components.GoogleSignInButton
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

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

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bgwelcome),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
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
                Text(
                    text = "Masuk",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F4C5C)
                )
                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )
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
                    leadingIcon = R.drawable.people
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        viewModel.loginEmail(
                            email = email,
                            pass = password,
                            onSuccess = {
                                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT)
                                    .show()
                                onLoginSucces()
                            },
                            onError = { pesan ->
                                Toast.makeText(context, pesan, Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF0092AF),
                                    Color(0xFF48BFD7)
                                )
                            )
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues()
                )
                {
                    Text(
                        text = "Masuk",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                GoogleSignInButton(
                    onTokenReceived = { token ->
                        viewModel.loginWithGoogle(
                            idToken = token,
                            onSuccess = {
                                Toast.makeText(context, "Selamat Datang!", Toast.LENGTH_SHORT)
                                    .show()
                                onLoginSucces()
                            },
                            onError = { pesanError ->
                                Toast.makeText(
                                    context,
                                    "Login Gagal: $pesanError",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        )
                    },
                    onError = { pesan ->
                        Toast.makeText(context, "Google Sign In Dibatalkan", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                val annotatedString = buildAnnotatedString {
                    append("Belum Punya Akun? ")
                    pushStringAnnotation(tag = "register", annotation = "register")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF2CB9D1),
                            fontWeight = FontWeight.Bold
                        )
                    ) {
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
            if (viewModel.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.CircularProgressIndicator(
                        color = Color(0xFF2CB9D1)
                    )
                }
            }
        }
    }
}