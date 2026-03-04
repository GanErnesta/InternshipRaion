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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomBottomNav() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Beranda", "Aktivitas", "Pembayaran", "Profil")
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
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
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
                        contentDescription = item,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(item, fontSize = 10.sp) },
                selected = selectedItem == index,
                onClick = { selectedItem = index },
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