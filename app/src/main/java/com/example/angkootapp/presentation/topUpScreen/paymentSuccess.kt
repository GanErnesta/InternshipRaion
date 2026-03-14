package com.example.angkootapp.presentation.topUpScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.angkootapp.R
import com.example.angkootapp.presentation.navigation.Screen
import kotlinx.coroutines.delay

// --- DEFINISI WARNA ---
private val BackgroundWhite = Color(0xFFFFFFFF)
private val DarkBlueText = Color(0xFF0D303A)
private val GrayDescription = Color(0xFF88989C)
private val TealLightBackground = Color(0xFFE5F7F7)
private val TealBadge = Color(0xFFDAEDED)

@Composable
fun OrderSuccessScreen(
    navController: NavController,
) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(5000)
        isLoading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = TealPrimary)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Menyelesaikan pesanan...",
                    color = GrayDescription,
                    fontSize = 14.sp
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(112.dp))

                IconCentang()

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Pesanan Berhasil!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlueText
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Angkotmu sedang dalam perjalanan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = GrayDescription
                )

                Spacer(modifier = Modifier.height(64.dp))

                DetailPesananCard()

                Spacer(modifier = Modifier.weight(1f))

                TombolLacak(navController = navController)

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


@Composable
private fun IconCentang() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(color = TealLightBackground, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.check),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(56.dp)
        )
    }
}

@Composable
private fun DetailPesananCard() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = BackgroundWhite),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            DetailItem(
                iconRes = R.drawable.ic_mobil,
                label = "Jenis angkot",
                value = "ADL",
                isBadgeValue = true
            )

            HorizontalDivider(
                color = Color(0xFFEEF2F5),
                thickness = 1.dp,
                modifier = Modifier.padding(start = 64.dp, end = 16.dp)
            )

            DetailItem(
                iconRes = R.drawable.ic_lokasi_pin,
                label = "Penjemputan",
                value = "Jalan Veteran no 5"
            )

            HorizontalDivider(
                color = Color(0xFFEEF2F5),
                thickness = 1.dp,
                modifier = Modifier.padding(start = 64.dp, end = 16.dp)
            )

            DetailItem(
                iconRes = R.drawable.ic_uang,
                label = "Total harga",
                value = "Rp 5.000",
                isValueTeal = true
            )
        }
    }
}

@Composable
private fun DetailItem(
    iconRes: Int,
    label: String,
    value: String,
    isBadgeValue: Boolean = false,
    isValueTeal: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(color = TealLightBackground, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = TealPrimary,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = GrayDescription,
            modifier = Modifier.weight(1f)
        )

        if (isBadgeValue) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlueText,
                modifier = Modifier
                    .background(color = TealBadge, shape = RoundedCornerShape(50.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            )
        } else {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isValueTeal) TealPrimary else DarkBlueText
            )
        }
    }
}

@Composable
private fun TombolLacak(
    navController: NavController
) {
    Button(
        onClick = { navController.navigate(Screen.Tracking.route) },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_lacak),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Lacak Angkot",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}