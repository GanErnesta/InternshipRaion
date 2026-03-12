package com.example.angkootapp.presentation.profil

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.angkootapp.R

@Composable
fun AkunScreen(
    navController: NavController
) {
    var namaLengkap by remember { mutableStateOf("Raion Community") }
    var noTelp by remember { mutableStateOf("08123456789") }
    var email by remember { mutableStateOf("user@gmail.com") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F7))
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 8.dp)
        ) {
            // Tombol back
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nama Lengkap
            AkunInputField(
                label = "NAMA LENGKAP",
                value = namaLengkap,
                onValueChange = { namaLengkap = it },
                iconRes = R.drawable.ic_akun_akun
            )

            // No Telp
            AkunInputField(
                label = "NO TELP",
                value = noTelp,
                onValueChange = { noTelp = it },
                iconRes = R.drawable.ic_telp
            )

            // Email
            AkunInputField(
                label = "EMAIL",
                value = email,
                onValueChange = { email = it },
                iconRes = R.drawable.ic_email_akun
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
    iconRes: Int
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
                unfocusedTextColor = Color(0xFF003F4B)
            ),
            singleLine = true
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AkunScreenPreview() {
    AkunScreen(navController = rememberNavController())
}
