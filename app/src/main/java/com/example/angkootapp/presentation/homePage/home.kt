package com.example.angkootapp.presentation.homePage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.angkootapp.R
import com.example.angkootapp.model.data.AngkotLocation
import com.example.angkootapp.model.data.TerminalLocation
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

fun bitmapFromResource(context: Context, resId: Int, width: Int, height: Int): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(context, resId)
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable?.setBounds(0, 0, canvas.width, canvas.height)
    drawable?.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

@SuppressLint("MissingPermission")
@Composable
fun MapPage() {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val angkotList = remember { mutableStateListOf<AngkotLocation>() }
    val terminalList = remember { mutableStateListOf<TerminalLocation>() }

    var mapsInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        MapsInitializer.initialize(context, MapsInitializer.Renderer.LATEST) {
            mapsInitialized = true
        }
        angkotList.add(AngkotLocation("1", "N 1234 AB", LatLng(-7.9252407, 112.59555)))
        angkotList.add(AngkotLocation("2", "N 5678 CD", LatLng(-7.9336105, 112.6559776)))
        angkotList.add(AngkotLocation("3", "N 1011 EF", LatLng(-8.0261702,112.6429637)))

        terminalList.add(TerminalLocation("1","Arjosari", LatLng(-7.9336264,112.6576808)))
        terminalList.add(TerminalLocation("2","Landungsari", LatLng(-7.924980, 112.598046)))
        terminalList.add(TerminalLocation("3","Hamid Rusdi", LatLng(-8.0261702,112.6429637)))
    }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasLocationPermission = granted }
    var isInitialCameraSet by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-7.983908, 112.621391), 12f)
    }
    val coroutineScope = rememberCoroutineScope()
    val angkotIcon = remember(mapsInitialized) {
        if (mapsInitialized) {
            bitmapFromResource(context, R.drawable.angkot_icon, 75, 90) // Ubah size di sini
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
                            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(latLng, 18f), 800)
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
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
        uiSettings = MapUiSettings(myLocationButtonEnabled = true)
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
}