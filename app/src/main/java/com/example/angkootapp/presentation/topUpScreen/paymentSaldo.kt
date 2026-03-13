package com.example.angkootapp.presentation.topUpScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentSaldoScreen(
    onBackClick: () -> Unit,
    onConfirmClick: (String) -> Unit
) {
    var nominalInput by remember { mutableStateOf("") }
    val quickNominals = listOf("20k", "50k", "100k")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Top up saldo",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF003F4B)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color(0xFF268696)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // 1. Header Section (Angkoot Wallet)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF268696)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.DirectionsBus, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "ANGKOOT WALLET",
                        fontSize = 12.sp,
                        color = Color(0xFF268696),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Isi Saldo Perjalanan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF003F4B)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Input Nominal Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Masukkan Nominal", fontSize = 14.sp, color = Color.Gray)

                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            "Rp. ",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        BasicTextField(
                            value = nominalInput,
                            onValueChange = { nominalInput = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            decorationBox = { innerTextField ->
                                Column {
                                    innerTextField()
                                    Divider(
                                        color = Color(0xFFD1E6E9),
                                        thickness = 2.dp,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        )
                    }
                    Text("Minimal top up Rp 10.000", fontSize = 11.sp, color = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3. Pilih Nominal Cepat
            Text(
                "Pilih Nominal Cepat",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF556E7A)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                quickNominals.forEach { amount ->
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .clickable {
                                nominalInput = amount.replace("k", "000")
                            },
                        shape = RoundedCornerShape(25.dp),
                        border = borderStroke(1.dp, Color(0xFFE0E0E0)),
                        color = Color.White
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = amount, color = Color.Gray, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Info Box
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFE0F2F1).copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF268696),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Saldo akan langsung masuk ke akun Angkoot Anda setelah pembayaran berhasil diverifikasi. Biaya admin mungkin berlaku tergantung metode pembayaran.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 5. Tombol Konfirmasi
            Button(
                onClick = { onConfirmClick(nominalInput) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF268696))
            ) {
                Text(
                    "Konfirmasi",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// Helper function untuk border
@Composable
fun borderStroke(width: androidx.compose.ui.unit.Dp, color: Color) =
    androidx.compose.foundation.BorderStroke(width, color)