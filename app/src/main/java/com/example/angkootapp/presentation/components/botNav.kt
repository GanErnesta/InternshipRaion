package com.example.angkootapp.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.angkootapp.presentation.navigation.Screen

@Composable
fun CustomBottomNav(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items: List<Screen> = listOf(
        Screen.MapsScreen,
        Screen.Activity,
        Screen.Payment,
        Screen.Profile
    )
    val labels = listOf("Beranda", "Aktivitas", "Pembayaran", "Profil")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.History,
        Icons.Default.Payment,
        Icons.Default.Person
    )

    NavigationBar(
        modifier = Modifier
            .height(80.dp)
            .graphicsLayer {
                shadowElevation = 30f
                clip = true
                translationY = -4f
            },
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        icons[index],
                        contentDescription = labels[index],
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(labels[index], fontSize = 10.sp) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF2CB9D1),
                    selectedTextColor = Color(0xFF2CB9D1),
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}