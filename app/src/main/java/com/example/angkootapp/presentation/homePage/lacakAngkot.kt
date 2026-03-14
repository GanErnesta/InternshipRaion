package com.example.angkootapp.presentation.homePage

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.angkootapp.R
import com.example.angkootapp.model.data.RouteData
import com.example.angkootapp.presentation.navigation.Screen
import com.example.angkootapp.ui.theme.primaryColor
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.delay

@Composable
fun TrackingScreen(
    navController: NavController,
    savedStepIndex: Int,
    savedIsHeadingToKos: Boolean,
    onProgressUpdate: (Float, Int, Boolean) -> Unit
) {
    val routeJemput = remember { RouteData.UB_TO_LANDUNGSARI.reversed() }
    val routeAntar = remember { RouteData.UB_TO_KOS_SUHAT }

    var isHeadingToKos by remember { mutableStateOf(savedIsHeadingToKos) }
    var activeRoute by remember { mutableStateOf(if (isHeadingToKos) routeAntar else routeJemput) }
    var isFinished by remember { mutableStateOf(false) }

    var currentAngkotPos by remember {
        mutableStateOf(activeRoute[savedStepIndex.coerceAtMost(activeRoute.size - 1)])
    }
    var angkotRotation by remember { mutableFloatStateOf(0f) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentAngkotPos, 15f)
    }

    LaunchedEffect(Unit) {
        if (!isHeadingToKos) {
            for (i in savedStepIndex until routeJemput.size - 1) {
                val start = routeJemput[i]
                val end = routeJemput[i + 1]
                angkotRotation = calculateBearing(start, end)

                val animatable = Animatable(0f)
                animatable.animateTo(1f, tween(800, easing = LinearEasing)) {
                    currentAngkotPos = interpolateLatLng(value, start, end)
                    val p = (i.toFloat() / (routeJemput.size - 1)) * 0.5f
                    onProgressUpdate(p, i, false) // Simpan progress, index, dan fase jemput
                }
            }
            isHeadingToKos = true
            activeRoute = routeAntar
            onProgressUpdate(0.5f, 0, true)
            delay(1500)
        }

        val startStep = if (savedIsHeadingToKos) savedStepIndex else 0
        for (i in startStep until routeAntar.size - 1) {
            val start = routeAntar[i]
            val end = routeAntar[i + 1]
            angkotRotation = calculateBearing(start, end)

            val animatable = Animatable(0f)
            animatable.animateTo(1f, tween(1000, easing = LinearEasing)) {
                currentAngkotPos = interpolateLatLng(value, start, end)
                cameraPositionState.move(CameraUpdateFactory.newLatLng(currentAngkotPos))
                val p = 0.5f + ((i.toFloat() / (routeAntar.size - 1)) * 0.5f)
                onProgressUpdate(p, i, true)
            }
        }
        isFinished = true
        onProgressUpdate(1f, routeAntar.size - 1, true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            Polyline(points = activeRoute, color = Color(0xFF2CB9D1), width = 12f)
            Marker(
                state = MarkerState(position = currentAngkotPos),
                icon = BitmapDescriptorFactory.fromResource(R.drawable.angkot_icon),
                rotation = angkotRotation,
                anchor = Offset(0.5f, 0.5f),
                flat = true
            )
            Marker(
                state = MarkerState(position = activeRoute.last()),
                title = if (isHeadingToKos) "Kos Suhat" else "UB"
            )
        }

        // UI NOTIFIKASI ATAS
        if (!isFinished) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .statusBarsPadding()
                    .align(Alignment.TopCenter),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.angkot_confirm),
                        contentDescription = null,
                        tint = Color(0xFF2CB9D1),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (!isHeadingToKos) "Angkot sedang menuju lokasi penjemputan"
                        else "Angkot sedang menuju lokasi tujuan anda",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )
                }
            }
        }

        if (isFinished) {
            EndTripOverlay(navController)
        }
    }
}

@Composable
fun EndTripOverlay(navController: NavController) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    null,
                    tint = Color(0xFF2CB9D1),
                    modifier = Modifier.size(64.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text("Yeaay!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = primaryColor)
                Text(
                    "Anda sudah sampai di akhir perjalanan",
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        navController.navigate(Screen.MapsScreen.route) {
                            popUpTo(Screen.Tracking.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2CB9D1))
                ) { Text("Selesaikan Perjalanan", color = Color.White) }
            }
        }
    }
}

fun interpolateLatLng(fraction: Float, from: LatLng, to: LatLng): LatLng {
    return LatLng(
        from.latitude + (to.latitude - from.latitude) * fraction,
        from.longitude + (to.longitude - from.longitude) * fraction
    )
}

fun calculateBearing(begin: LatLng, end: LatLng): Float {
    val lat1 = Math.toRadians(begin.latitude); val lng1 = Math.toRadians(begin.longitude)
    val lat2 = Math.toRadians(end.latitude); val lng2 = Math.toRadians(end.longitude)
    val y = Math.sin(lng2 - lng1) * Math.cos(lat2)
    val x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lng2 - lng1)
    return ((Math.toDegrees(Math.atan2(y, x)) + 360) % 360).toFloat()
}