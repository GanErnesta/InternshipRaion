package com.example.angkootapp.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.angkootapp.R
import com.example.angkootapp.presentation.navigation.Screen

@Composable
fun CustomBottomNav(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        Screen.MapsScreen,
        Screen.Activity,
        Screen.Booklet,
        Screen.Profile
    )

    val labels = listOf("Beranda", "Aktivitas", "Booklet", "Profil")

    val icons = listOf(
        R.drawable.home,
        R.drawable.aktivitas,
        R.drawable.booklet,
        R.drawable.profile
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
                        // Panggil painterResource di sini
                        painter = painterResource(id = icons[index]),
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