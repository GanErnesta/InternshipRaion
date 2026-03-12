package com.example.angkootapp.presentation.notifPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkootapp.R

@Composable
fun NotifScreen(
    onDismiss: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F7)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_notif_empty),
                contentDescription = "Tidak ada notifikasi",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .offset(y = 16.dp)
            )

            Spacer(modifier = Modifier.height(0.dp))

            Text(
                text = "Tidak ada notifikasi",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A2E),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Kami akan memberitahumu jika terdapat notifikasi",
                fontSize = 16.sp,
                color = Color(0xFF888EA8),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(49.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(50.dp),
                        ambientColor = Color(0xFF29B6C5),
                        spotColor = Color(0xFF0077A8)
                    )
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF0092AF),
                                Color(0xFF3BBFBF)
                            )
                        ),
                        shape = RoundedCornerShape(50.dp)
                    ),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Ok",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotifScreenPreview() {
    NotifScreen()
}