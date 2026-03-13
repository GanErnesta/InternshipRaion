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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopUpSaldoScreen(
    onBackClick: () -> Unit,
    onConfirmClick: (String) -> Unit
) {
    var nominalInput by remember { mutableStateOf("0") }
    val quickNominals = listOf("20k", "50k", "100k")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Top up saldo", fontWeight = FontWeight.Bold, color = Color(0xFF003F4B))
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0F2F1))
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color(0xFF268696),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
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
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Header: Angkoot Wallet Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF268696)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.DirectionsBus,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "ANGKOOT WALLET",
                        fontSize = 11.sp,
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

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Input Nominal Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Masukkan Nominal", fontSize = 14.sp, color = Color.Gray)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Rp. ",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF003F4B)
                        )
                        BasicTextField(
                            value = nominalInput,
                            onValueChange = { if (it.all { c -> c.isDigit() }) nominalInput = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF003F4B)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    // Underline effect
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFE0E0E0)))

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Minimal top up Rp 10.000", fontSize = 12.sp, color = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3. Quick Selection Section
            Text(
                "Pilih Nominal Cepat",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                quickNominals.forEach { label ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(25.dp))
                            .clickable {
                                nominalInput = when(label) {
                                    "20k" -> "20000"
                                    "50k" -> "50000"
                                    "100k" -> "100000"
                                    else -> nominalInput
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(label, color = Color.Gray, fontSize = 14.sp)
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
                        null,
                        tint = Color(0xFF268696),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Saldo akan langsung masuk ke akun Angkoot Anda setelah pembayaran berhasil diverifikasi. Biaya admin mungkin berlaku tergantung metode pembayaran.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 5. Confirmation Button
            Button(
                onClick = { onConfirmClick(nominalInput) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF33B9C9))
            ) {
                Text("Konfirmasi", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}