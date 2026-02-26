package com.example.angkootapp.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthHeader(title: String) {
    Text(
        text = title,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF0F4C5C)
    )
    Spacer(modifier = Modifier.height(32.dp))
}