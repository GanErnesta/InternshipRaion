package com.example.angkootapp.presentation.activityScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.navigation.NavController
import com.example.angkootapp.R
import com.example.angkootapp.presentation.navigation.Screen
import com.example.angkootapp.ui.theme.primaryColor

@Composable
fun ActivityScreen(navController: NavController, currentProgress: Float) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F7))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Aktivitas",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF003F4B)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color(0xFFe1f1f1))
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("Sedang Berjalan", "Riwayat").forEachIndexed { index, label ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                if (selectedTab == index) Color(0xFFF6F8F8) else Color.Transparent
                            )
                            .padding(vertical = 8.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { selectedTab = index },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (selectedTab == index) Color(0xFF1CA6A6) else Color(
                                0xFF475569
                            )
                        )
                    }
                }
            }
        }

        if (selectedTab == 0) {
            SedangBerjalanContent(navController, currentProgress)
        } else {
            RiwayatContent()
        }
    }
}

@Composable
fun SedangBerjalanContent(navController: NavController, progress: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Screen.Tracking.route) },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color(0xFFF0FDF4))
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(Color(0xFF4CAF50))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Live Status",
                            fontSize = 12.sp,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Id. Pesanan", fontSize = 10.sp, color = Color(0xFFBDBDBD))
                        Text(
                            text = "#ADL-001",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF29B6C5)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Angkot ADL",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A2E)
                )
                Text(
                    text = "Arjosari - Dinoyo - Landungsari",
                    fontSize = 13.sp,
                    color = Color(0xFF29B6C5)
                )

                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(id = R.drawable.ic_supir),
                            null,
                            Modifier.size(16.dp),
                            Color(0xFF888EA8)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Mulyono", fontSize = 13.sp, color = Color(0xFF64748B))
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(id = R.drawable.ic_plate),
                            null,
                            Modifier.size(16.dp),
                            Color(0xFF888EA8)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("N 1234 AB", fontSize = 13.sp, color = Color(0xFF64748B))
                    }
                }

                Spacer(modifier = Modifier.height(34.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .align(Alignment.Center),
                        color = Color(0xFF29B6C5),
                        trackColor = Color(0xFFE0E0E0)
                    )
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color(0xFF29B6C5))
                            .align(Alignment.CenterStart)
                    )
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(if (progress >= 1f) Color(0xFF29B6C5) else Color(0xFFBDBDBD))
                            .align(Alignment.CenterEnd)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Beli", fontSize = 11.sp, color = Color(0xFF2F9E9E))
                    Text(text = "Sampai", fontSize = 11.sp, color = Color(0xFFBDBDBD))
                }

                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Estimasi Sampai :", fontSize = 13.sp, color = Color(0xFFBDBDBD))
                    Text(
                        text = if (progress >= 1f) "Sampai" else "09:10 AM",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF29B6C5)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(38.dp))
        Text(
            "Rincian Perjalanan",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = primaryColor,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column {
                RincianItem(R.drawable.ic_payment, "Metode Pembayaran", "QRIS")
                HorizontalDivider(color = Color(0xFFF2F4F7))
                RincianItem(R.drawable.ic_tarif, "Total Tarif", "Rp 5.000")
                HorizontalDivider(color = Color(0xFFF2F4F7))
                RincianItem(R.drawable.ic_penumpang, "Penumpang", "1 Orang")
                HorizontalDivider(color = Color(0xFFF2F4F7))
                RincianItem(
                    R.drawable.ic_oksigen,
                    "CO2 Terselamatkan",
                    "0.8 kg",
                    Color(0xFF4CAF50),
                    backgroundColor = Color(0xFFf4fafa)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RincianItem(
    iconRes: Int,
    label: String,
    value: String,
    valueColor: Color = Color(0xFF1A1A2E),
    backgroundColor: Color = Color.Transparent
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(id = iconRes), null, Modifier.size(28.dp), Color.Unspecified)
            Spacer(Modifier.width(12.dp))
            Text(text = label, fontSize = 13.sp, color = Color(0xFF64748B))
        }
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = valueColor)
    }
}