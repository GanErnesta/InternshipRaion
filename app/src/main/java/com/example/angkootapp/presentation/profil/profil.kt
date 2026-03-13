package com.example.angkootapp.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle // Import baru
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.angkootapp.R
import com.example.angkootapp.model.viewModel.ProfileUiState
import com.example.angkootapp.model.viewModel.ProfileViewModel

private val BackgroundGray = Color(0xFFFFFFFF)
private val CardWhite = Color.White
private val TextDark = Color(0xFF1A1A2E)
private val TextGray = Color(0xFF888888)
private val IconBackground = Color(0xFF3BBFBF).copy(alpha = 0.12f)
private val TealPrimary = Color(0xFF3BBFBF)

object ProfileRoutes {
    const val MY_ACCOUNT = "akun"
    const val LOCATION = "location"
    const val CHANGE_PASS = "change_password"
    const val TERMS = "syarat"
    const val TOP_UP = "topup"
}

@Composable
private fun screenWidth(): Dp = LocalConfiguration.current.screenWidthDp.dp

@Composable
private fun screenHeight(): Dp = LocalConfiguration.current.screenHeightDp.dp

@Composable
fun ProfilScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    // SINKRONISASI DISINI: Mengambil data real-time dari ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = TealPrimary
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ProfilHeader(uiState = uiState)
                ProfilStatsCard(uiState = uiState, navController = navController)
                ProfilMenuSection(navController = navController, viewModel = viewModel)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Menampilkan pesan error jika ada
        uiState.error?.let { msg ->
            Text(
                text = msg,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            )
        }
    }
}

@Composable
private fun ProfilHeader(uiState: ProfileUiState) {
    val headerHeight = screenHeight() * 0.38f
    val avatarSize = screenWidth() * 0.22f
    val bottomPad = headerHeight * 0.15f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_profil),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = bottomPad)
        ) {
            Text(
                text = "Profil",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // FOTO SINKRON: Menggunakan photoUrl dari Firestore
            AsyncImage(
                model = uiState.photoUrl.ifEmpty { null },
                contentDescription = "Foto Profil",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_akun_profil),
                error = painterResource(id = R.drawable.ic_akun_profil),
                modifier = Modifier
                    .size(avatarSize)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // NAMA SINKRON: Menggunakan name dari Firestore
            Text(
                text = uiState.name.ifEmpty { "Nama Pengguna" },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            // TELEPON SINKRON
            Text(
                text = uiState.phone.ifEmpty { "-" },
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
private fun ProfilStatsCard(uiState: ProfileUiState, navController: NavController) {
    val cardOffset = screenHeight() * 0.075f
    val hPadding = screenWidth() * 0.065f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = hPadding)
            .offset(y = -cardOffset),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // SALDO SINKRON
            StatItem(
                iconResId = R.drawable.ic_tambah,
                label = "Top Up Saldo",
                value = uiState.walletBalance,
                modifier = Modifier.clickable { navController.navigate(ProfileRoutes.TOP_UP) }
            )
            // JUMLAH PERJALANAN/DOMPET SINKRON
            StatItem(
                iconResId = R.drawable.dompet,
                label = "Dompet",
                value = uiState.tripCount.toString()
            )
            StatItem(
                iconResId = R.drawable.perjalanan,
                label = "Perjalanan",
                value = uiState.featureCount.toString()
            )
        }
    }
}

@Composable
private fun ProfilMenuSection(navController: NavController, viewModel: ProfileViewModel) {
    val hPadding = screenWidth() * 0.10f
    val menuOffset = screenHeight() * 0.038f

    Column(
        modifier = Modifier
            .padding(horizontal = hPadding)
            .offset(y = -menuOffset)
    ) {
        Text(
            text = "Privasi & Pengaturan",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF003F4B),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                MenuRow(R.drawable.ic_akun_profil, "Akun Saya") {
                    navController.navigate(
                        ProfileRoutes.MY_ACCOUNT
                    )
                }
                MenuRow(R.drawable.ic_lokasi_profil, "Lokasi") {
                    navController.navigate(
                        ProfileRoutes.LOCATION
                    )
                }
                MenuRow(R.drawable.ic_gembok_profil, "Ganti Kata Sandi") {
                    navController.navigate(
                        ProfileRoutes.CHANGE_PASS
                    )
                }

                // Tambahan Tombol Logout biar fungsional
                MenuRow(R.drawable.ic_syarat_profil, "Keluar Akun") {
                    viewModel.logout()
                    navController.navigate("login") { popUpTo(0) }
                }
            }
        }
    }
}

@Composable
private fun StatItem(iconResId: Int, label: String, value: String, modifier: Modifier = Modifier) {
    val iconSize = screenWidth() * 0.125f
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(8.dp)) {
        Box(
            modifier = Modifier
                .size(iconSize)
                .clip(CircleShape)
                .background(IconBackground),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = label,
                modifier = Modifier.size(iconSize * 0.44f)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, fontSize = 12.sp, color = TextGray)
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF003F4B)
        )
    }
}

@Composable
private fun MenuRow(iconResId: Int, label: String, onClick: () -> Unit) {
    val iconBoxSize = screenWidth() * 0.095f
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(iconBoxSize)
                .clip(CircleShape)
                .background(IconBackground),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = label,
                modifier = Modifier.size(iconBoxSize * 0.53f)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Text(text = label, fontSize = 14.sp, color = TextDark, modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextGray,
            modifier = Modifier.size(20.dp)
        )
    }
}