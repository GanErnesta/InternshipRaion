package com.example.angkootapp.presentation.profil

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.angkootapp.R
import com.example.angkootapp.model.viewModel.ProfileViewModel

@Composable
fun AkunScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var namaLengkap by remember { mutableStateOf("") }
    var noTelp by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(uiState.name, uiState.phone, uiState.email) {
        namaLengkap = uiState.name
        noTelp = uiState.phone
        email = uiState.email
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F7))
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 8.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Kembali",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(36.dp)
                )
            }

            Text(
                text = "Akun Saya",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF003F4B),
                modifier = Modifier.align(Alignment.Center)
            )

            TextButton(
                onClick = {
                    viewModel.updateProfile(namaLengkap, noTelp) { success ->
                        if (success) {
                            Toast.makeText(context, "Profil diperbarui!", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp),
                enabled = !uiState.isLoading
            ) {
                Text(
                    text = if (uiState.isLoading) "..." else "Simpan",
                    color = Color(0xFF3BBFBF),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading && namaLengkap.isEmpty()) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color(0xFF3BBFBF))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AkunInputField(
                label = "NAMA LENGKAP",
                value = namaLengkap,
                onValueChange = { namaLengkap = it },
                iconRes = R.drawable.ic_akun_akun
            )

            AkunInputField(
                label = "NO TELP",
                value = noTelp,
                onValueChange = { noTelp = it },
                iconRes = R.drawable.ic_telp
            )

            AkunInputField(
                label = "EMAIL",
                value = email,
                onValueChange = {  },
                iconRes = R.drawable.ic_email_akun,
                readOnly = true
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun AkunInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    iconRes: Int,
    readOnly: Boolean = false
) {
    Column {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF64748B),
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp),
            readOnly = readOnly,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFF3BBFBF)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3BBFBF),
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color(0xFFEEF2F5),
                unfocusedContainerColor = Color(0xFFEEF2F5),
                focusedTextColor = Color(0xFF003F4B),
                unfocusedTextColor = if (readOnly) Color.Gray else Color(0xFF003F4B)
            ),
            singleLine = true
        )
    }
}