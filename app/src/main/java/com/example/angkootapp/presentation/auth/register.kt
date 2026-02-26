package com.example.angkootapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
    var isChecked by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmpasswordVisible by remember { mutableStateOf(false) }


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
                .fillMaxHeight(0.85f)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Daftar",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F4C5C)
                )

                Spacer(modifier = Modifier.height(24.dp))

                CustomInputField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Nama Lengkap",
                    placeholder = "Masukkan Nama",
                    leadingIcon = R.drawable.people
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomInputField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = "No. Telp",
                    placeholder = "Masukkan No. Telp",
                    leadingIcon = R.drawable.vector
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomInputField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    placeholder = "Masukkan Email",
                    leadingIcon = R.drawable.vector
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomInputField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Kata Sandi",
                    placeholder = "Kata Sandi",
                    leadingIcon = R.drawable.people,
                    isPassword = true,
                    passwordVisible = passwordVisible,
                    onPasswordToggle = {passwordVisible = !passwordVisible}
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomInputField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Konfirmasi Kata Sandi",
                    placeholder = "Konfirmasi Kata Sandi",
                    leadingIcon = R.drawable.people,
                    isPassword = true,
                    passwordVisible = confirmpasswordVisible,
                    onPasswordToggle = {confirmpasswordVisible = !confirmpasswordVisible}
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it },
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF2CB9D1))
                    )
                    Text(
                        text = "Saya setuju dengan persyaratan dan penggunaan.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                PrimaryButton(
                    text = "Daftar",
                    onClick = {
                        if (password != confirmPassword) {
                            Toast.makeText(context, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                        } else if (!isChecked) {
                            Toast.makeText(context, "Setujui persyaratan dahulu", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.registerEmail(
                                email = email,
                                pass = password,
                                onSuccess = {
                                    Toast.makeText(context, "Berhasil Daftar!", Toast.LENGTH_SHORT).show()
                                    onRegisterSuccess()
                                },
                                onError = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    Text(
                        text = " Atau login dengan ",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                }
                val annotatedString = buildAnnotatedString {
                    append("Sudah Punya Akun? ")
                    pushStringAnnotation(tag = "login", annotation = "login")
                    withStyle(style = SpanStyle(color = Color(0xFF2CB9D1), fontWeight = FontWeight.Bold)) {
                        append("Masuk Sekarang!!")
                    }
                    pop()
                }

                Text(
                    text = annotatedString,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onLoginClick() }
                )
            }
        }

        if (viewModel.isLoading) {
            LoadingOverlay()
        }
    }
}