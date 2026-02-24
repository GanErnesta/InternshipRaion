package com.example.angkootapp.presentation.welcomePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.angkootapp.R
@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bgwelcome),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selamat Datang",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Lebih mudah naik angkot dengan angkoot",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .navigationBarsPadding()
                .align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onRegisterClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Daftar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 40.dp))
                    .background(Color(0xFFD9E1E5))
                    .clickable { onLoginClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Masuk",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F4C5C)
                )
            }
        }
    }
}