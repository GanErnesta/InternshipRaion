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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.angkootapp.R
import com.example.angkootapp.model.viewModel.ActivityViewModel

@Composable
fun OrderConfirmationDialog(
    namaAngkot: String,
    rute: String,
    harga: String,
    onDismissRequest: () -> Unit,
    onConfirm: (Context) -> Unit,
    viewModel: ActivityViewModel = viewModel()
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

                Spacer(modifier = Modifier.height(20.dp)) // Jarak antar gambar dan judul

                Text(
                    text = "Pesan $namaAngkot?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF001F3F),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp)) // Jarak antar judul dan rute

                Text(
                    text = "Rute: $rute",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp)) // Jarak kecil antara rute dan peringatan

                Text(
                    text = "Setelah anda klik pesan, angkot tidak dapat dibatalkan.",
                    fontSize = 13.sp,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp // Agar teks peringatan yang panjang tidak rapat antar baris
                )

                Spacer(modifier = Modifier.height(28.dp)) // Jarak sebelum tombol

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Tombol Batal
                    Button(
                        onClick = onDismissRequest,
                        modifier = Modifier.weight(1f).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F1F1), contentColor = Color.Gray),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Batal", fontWeight = FontWeight.Bold)
                    }

                    // Tombol Pesan (Dinamis)
                    Button(
                        onClick = {
                            try {
                                // Menyimpan data dinamis ke Firestore
                                viewModel.saveOrderToFirestore(
                                    namaAngkot = namaAngkot,
                                    rute = rute,
                                    hargaLabel = harga,
                                    tarif = 5000, // Bisa kamu buat dinamis juga jika perlu
                                    metode = "QRIS",
                                    penumpang = "1 Orang"
                                )
                                onConfirm(context)
                            } catch (e: Exception) {
                                Log.e("OrderDialog", "Error: ${e.message}")
                            }
                        },
                        modifier = Modifier.weight(1f).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2CB9D1)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Pesan", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}