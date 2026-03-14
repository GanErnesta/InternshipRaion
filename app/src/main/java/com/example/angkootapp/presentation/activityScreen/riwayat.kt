package com.example.angkootapp.presentation.activityScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkootapp.R
import com.example.angkootapp.model.data.OrderData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@Composable
fun RiwayatContent() {
    val db = FirebaseFirestore.getInstance()
    var orders by remember { mutableStateOf<List<OrderData>>(listOf()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        db.collection("orders")
            .whereEqualTo("status", "selesai")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    orders = value.toObjects(OrderData::class.java)
                }
                isLoading = false
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "RIWAYAT TERBARU",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF888EA8),
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF29B6C5))
            }
        } else if (orders.isEmpty()) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("Belum ada riwayat", color = Color.Gray)
            }
        } else {
            orders.forEach { order ->
                RiwayatItem(
                    iconRes = R.drawable.ic_mobil,
                    namaAngkot = order.namaAngkot,
                    info = "${order.tanggal}, ${order.jam} • ${order.hargaLabel}"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun RiwayatItem(iconRes: Int, namaAngkot: String, info: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE0F7FA)),
                contentAlignment = Alignment.Center
            ) {
                Icon(painterResource(iconRes), null, Modifier.size(24.dp), Color(0xFF29B6C5))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(namaAngkot, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(info, fontSize = 12.sp, color = Color(0xFF888EA8))
            }
            Icon(
                painterResource(R.drawable.ic_ceklis),
                null,
                Modifier.size(20.dp),
                Color(0xFF29B6C5)
            )
        }
    }
}