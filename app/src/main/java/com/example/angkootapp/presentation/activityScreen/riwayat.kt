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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkootapp.R
import androidx.compose.runtime.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@Composable
fun RiwayatContent() {
    val db = FirebaseFirestore.getInstance()
    var orders by remember { mutableStateOf<List<Map<String, Any>>>(listOf()) }

    // Efek untuk mengambil data saat halaman dibuka
    LaunchedEffect(Unit) {
        db.collection("orders")
            .whereEqualTo("status", "selesai")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    orders = value.documents.map { it.data ?: emptyMap() }
                }
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
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )

//        RiwayatItem(iconRes = R.drawable.ic_mobil, namaAngkot = "Angkot ADL", info = "Kemarin, 14:00 • 12K")
//        Spacer(modifier = Modifier.height(8.dp))
//        RiwayatItem(iconRes = R.drawable.ic_mobil, namaAngkot = "Angkot AG", info = "01 Mar, 09:15 • 15K")

        Spacer(modifier = Modifier.height(16.dp))

        orders.forEach { order ->
            val nama = order["namaAngkot"]?.toString() ?: "Angkot"
            val tgl = order["tanggal"]?.toString() ?: ""
            val jam = order["jam"]?.toString() ?: ""
            val harga = order["hargaLabel"]?.toString() ?: ""

            RiwayatItem(
                iconRes = R.drawable.ic_mobil,
                namaAngkot = nama,
                info = "$tgl, $jam • $harga"
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun RiwayatItem(
    iconRes: Int,
    namaAngkot: String,
    info: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFE0F7FA)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF29B6C5)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = namaAngkot, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A2E))
                    Text(text = info, fontSize = 12.sp, color = Color(0xFF888EA8))
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_ceklis),
                contentDescription = "Selesai",
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF29B6C5)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RiwayatContentPreview() {
    RiwayatContent()
}