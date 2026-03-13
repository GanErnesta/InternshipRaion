package com.example.angkootapp.presentation.topUpScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.angkootapp.model.data.TransactionData
import com.example.angkootapp.model.viewModel.TopUpViewModel
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.navigation.NavController

// Colors
val TealPrimary = Color(0xFF268696)
val TealLight = Color(0xFF4DB9C9)
val BgSoftTeal = Color(0xFFE0F2F1)
val SuccessGreen = Color(0xFF4CAF50)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourSaldoScreen(
    navController: NavController,
    onBackClick: () -> Unit,
    onTopUpClick: () -> Unit,
    viewModel: TopUpViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFFFFF),
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = TealPrimary)
                    }
                }
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            // 1. Saldo Card
            SaldoCard(saldo = uiState.saldo, onTopUpClick = onTopUpClick)

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Riwayat Button
            OutlinedButton(
                onClick = { /* Navigate to History */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50.dp),
                border = BorderStroke(1.dp, TealPrimary.copy(alpha = 0.2f)),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = BgSoftTeal.copy(alpha = 0.3f))
            ) {
                Icon(Icons.Default.History, null, tint = TealPrimary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Riwayat Transaksi", color = TealPrimary)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Aktivitas Terakhir Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Aktivitas Terakhir",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TealPrimary
                )
                Text(
                    "Lihat Semua",
                    color = TealPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { })
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. List Transaksi
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(uiState.transactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}

@Composable
fun SaldoCard(saldo: String, onTopUpClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Brush.verticalGradient(listOf(TealLight, TealPrimary)))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.AccountBalanceWallet,
                    null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Saldo Kamu", color = Color.White, fontSize = 16.sp)
            }
            Text(saldo, color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Button(
                onClick = onTopUpClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)

            ) {
                Icon(Icons.Default.AddCircleOutline, null, tint = TealPrimary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Top Up Saldo", color = TealPrimary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TransactionItem(data: TransactionData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(BgSoftTeal),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (data.isTopUp) Icons.Default.ArrowCircleDown else Icons.Default.DirectionsBus,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(data.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(data.date, color = Color.Gray, fontSize = 12.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    data.amount,
                    fontWeight = FontWeight.Bold,
                    color = if (data.isTopUp) SuccessGreen else Color.Black,
                    fontSize = 15.sp
                )
                Surface(
                    color = (if (data.isTopUp) SuccessGreen else Color.Gray).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = data.status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        fontSize = 11.sp,
                        color = if (data.isTopUp) SuccessGreen else Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}