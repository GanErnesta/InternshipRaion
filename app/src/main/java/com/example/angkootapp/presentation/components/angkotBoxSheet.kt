package com.example.angkootapp.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AngkotBottomSheet(
    onDismissRequest: () -> Unit,
    onConfirmOrder: (Context, Int, Int) -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    var passengerCount by remember { mutableIntStateOf(1) }
    var selectedAngkot by remember { mutableStateOf<String?>(null) }
    var selectedPayment by remember { mutableStateOf("Metode Pembayaran") }
    var expanded by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val paymentOptions = listOf("QRIS", "Tunai")

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xFFE0E0E0)) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 48.dp)
        ) {
            AngkotRow(
                name = "Angkot ADL",
                route = "Arjosari - Dinoyo - Landungsari",
                price = "Rp 5.000",
                seats = 4,
                isSelected = selectedAngkot == "ADL",
                onSelect = { selectedAngkot = "ADL" }
            )

            Spacer(modifier = Modifier.height(12.dp))

            AngkotRow(
                name = "Angkot AL",
                route = "Arjosari - Landungsari",
                price = "Rp 5.000",
                seats = 2,
                isSelected = selectedAngkot == "AL",
                onSelect = { selectedAngkot = "AL" }
            )

            if (selectedAngkot != null) {
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        Row(
                            modifier = Modifier
                                .clickable { expanded = true }
                                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = when (selectedPayment) {
                                    "QRIS" -> Icons.Default.QrCodeScanner
                                    "Tunai" -> Icons.Default.Payments
                                    else -> Icons.Default.AccountBalanceWallet
                                },
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = Color(0xFF2CB9D1)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = selectedPayment,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedPayment == "Metode Pembayaran") Color.Gray else Color.Black
                            )
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Black)
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(Color.White).width(160.dp)
                        ) {
                            paymentOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option, color = Color.Black, fontSize = 14.sp) },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = if (option == "QRIS") Icons.Default.QrCodeScanner else Icons.Default.Payments,
                                            contentDescription = null,
                                            tint = Color(0xFF2CB9D1),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    onClick = {
                                        selectedPayment = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(Color(0xFFF1F9FB), CircleShape).padding(horizontal = 4.dp)
                    ) {
                        IconButton(onClick = { if (passengerCount > 1) passengerCount-- }) {
                            Icon(Icons.Default.Remove, contentDescription = null, tint = Color(0xFF2CB9D1))
                        }
                        Text(text = passengerCount.toString(), fontWeight = FontWeight.Bold, color = Color.Black)
                        IconButton(onClick = { passengerCount++ }) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF2CB9D1))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                val totalHarga = 5000 * passengerCount

                Button(
                    onClick = { showConfirmationDialog = true },
                    enabled = selectedPayment != "Metode Pembayaran",
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2CB9D1),
                        disabledContainerColor = Color(0xFFB0E4ED)
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Pesan Sekarang", fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Rp $totalHarga", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                if (showConfirmationDialog) {
                    OrderConfirmationDialog(
                        onDismissRequest = { showConfirmationDialog = false },
                        onConfirm = { ctx ->
                            val db = FirebaseFirestore.getInstance()
                            val now = Date()
                            val orderData = hashMapOf(
                                "namaAngkot" to (if (selectedAngkot == "ADL") "Angkot ADL" else "Angkot AL"),
                                "rute" to (if (selectedAngkot == "ADL") "Arjosari - Dinoyo - Landungsari" else "Arjosari - Landungsari"),
                                "tarif" to totalHarga,
                                "hargaLabel" to "${totalHarga / 1000}K",
                                "penumpang" to "$passengerCount Orang",
                                "metodePembayaran" to selectedPayment,
                                "status" to "berjalan",
                                "tanggal" to SimpleDateFormat("dd MMM", Locale("id", "ID")).format(now),
                                "jam" to SimpleDateFormat("HH:mm", Locale("id", "ID")).format(now),
                                "timestamp" to Timestamp(now),
                                "co2Saved" to 0.8
                            )

                            db.collection("orders").add(orderData)
                                .addOnSuccessListener {
                                    showConfirmationDialog = false
                                    onConfirmOrder(ctx, totalHarga, passengerCount)
                                    onDismissRequest()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(ctx, "Gagal memesan.", Toast.LENGTH_SHORT).show()
                                }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AngkotRow(
    name: String,
    route: String,
    price: String,
    seats: Int,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color(0xFF2CB9D1) else Color(0xFFF1F1F1),
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                    if (isSelected) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(color = Color(0xFFE1F5F9), shape = RoundedCornerShape(8.dp)) {
                            Text(
                                text = "Dipilih",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                fontSize = 10.sp,
                                color = Color(0xFF2CB9D1),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Text(text = price, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
            }
            Text(text = route, fontSize = 13.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "● $seats kursi tersedia", fontSize = 13.sp, color = Color(0xFF4CAF50))
                Text(text = "🕒 12 menit", fontSize = 13.sp, color = Color.LightGray)
            }
        }
    }
}