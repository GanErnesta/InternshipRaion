package com.example.angkootapp.presentation.homePage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Looper
import android.location.Geocoder
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.angkootapp.R
import com.example.angkootapp.model.data.AngkotLocation
import com.example.angkootapp.model.data.TerminalLocation
import com.example.angkootapp.presentation.components.MapSearchBar
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import java.util.Locale

fun bitmapFromResource(context: Context, resId: Int, width: Int, height: Int): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(context, resId)
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable?.setBounds(0, 0, canvas.width, canvas.height)
    drawable?.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun searchLocation(context: Context, query: String): LatLng? {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocationName(query, 1)
        if (!addresses.isNullOrEmpty()) {
            LatLng(addresses[0].latitude, addresses[0].longitude)
        } else null
    } catch (e: Exception) {
        null
    }
}

@SuppressLint("MissingPermission")
@Composable
fun MapPage() {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val angkotList = remember { mutableStateListOf<AngkotLocation>() }
    val terminalList = remember { mutableStateListOf<TerminalLocation>() }
    var mapsInitialized by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        MapsInitializer.initialize(context, MapsInitializer.Renderer.LATEST) {
            mapsInitialized = true
        }
        angkotList.add(AngkotLocation("1", "N 1234 AB", LatLng(-7.9252407, 112.59555)))
        angkotList.add(AngkotLocation("2", "N 5678 CD", LatLng(-7.9336105, 112.6559776)))
        angkotList.add(AngkotLocation("3", "N 1011 EF", LatLng(-8.0261702, 112.6429637)))

        terminalList.add(TerminalLocation("1", "Arjosari", LatLng(-7.9336264, 112.6576808)))
        terminalList.add(TerminalLocation("2", "Landungsari", LatLng(-7.924980, 112.598046)))
        terminalList.add(TerminalLocation("3", "Hamid Rusdi", LatLng(-8.0261702, 112.6429637)))
    }

    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasLocationPermission = granted }

    var isInitialCameraSet by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-7.983908, 112.621391), 12f)
    }

    val angkotIcon = remember(mapsInitialized) {
        if (mapsInitialized) {
            bitmapFromResource(context, R.drawable.angkot_icon, 75, 90)
        } else {
            null
        }
    }

    DisposableEffect(hasLocationPermission) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return@DisposableEffect onDispose {}
        }
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    val latLng = LatLng(location.latitude, location.longitude)
                    if (!isInitialCameraSet) {
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(
                                    latLng,
                                    18f
                                ), 800
                            )
                            isInitialCameraSet = true
                        }
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000L).build(),
            locationCallback,
            Looper.getMainLooper()
        )
        onDispose { fusedLocationClient.removeLocationUpdates(locationCallback) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
            uiSettings = MapUiSettings(myLocationButtonEnabled = false)
        ) {
            if (angkotIcon != null) {
                angkotList.forEach { angkot ->
                    Marker(
                        state = MarkerState(position = angkot.position),
                        title = "Angkot: ${angkot.platNomor}",
                        icon = angkotIcon
                    )
                }
            }
            terminalList.forEach { terminal ->
                Marker(
                    state = MarkerState(position = terminal.position),
                    title = "Terminal: ${terminal.name}"
                )
            }
        }

        MapSearchBar(
            modifier = Modifier.statusBarsPadding(),
            onSearch = { query ->
                coroutineScope.launch {
                    val result = searchLocation(context, query)
                    if (result != null) {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(result, 15f), 1000
                        )
                    } else {
                        Toast.makeText(context, "Lokasi tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        location?.let {
                            coroutineScope.launch { // Launch coroutine lagi untuk animasi
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 18f)
                                )
                            }
                        }
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd) // Menyelaraskan ke kanan bawah Box
                .padding(bottom = 120.dp, end = 12.dp),
            containerColor = Color.White,
            contentColor = Color.Blue,
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Default.MyLocation, contentDescription = "My Location")
        }
    }
}