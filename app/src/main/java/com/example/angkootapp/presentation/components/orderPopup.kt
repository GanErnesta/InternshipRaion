package com.example.angkootapp.presentation.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.angkootapp.R

@Composable
fun OrderConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (Context) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        val context = LocalContext.current
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.angkot_confirm),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Anda yakin ingin memesan?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF001F3F),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Setelah anda klik pesan, angkot yang anda pesan tidak dapat dibatalkan.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismissRequest,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF1F1F1),
                            contentColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Batal", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            try {
                                onConfirm(context)
                            } catch (e: Exception) {
                                Log.e("OrderDialog", "Error saat klik pesan: ${e.message}")
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2CB9D1)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Pesan", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}