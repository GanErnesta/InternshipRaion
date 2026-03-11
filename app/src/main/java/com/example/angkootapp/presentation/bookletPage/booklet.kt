package com.example.angkootapp.presentation.bookletPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.angkootapp.R
import androidx.compose.ui.platform.LocalUriHandler

private val TealPrimary    = Color(0xFF3BBFBF)
private val TealLight      = Color(0xFFE8F8F8)
private val TextDark       = Color(0xFF1A1A2E)
private val TextGray       = Color(0xFF888888)
private val OrangeSoft     = Color(0xFFFFF0E6)
private val OrangeIcon     = Color(0xFFFF8C42)
private val BlueSoft       = Color(0xFFE8F4FF)
private val BlueIcon       = Color(0xFF4A90D9)
private val BackgroundPage = Color(0xFFF8FFFE)

data class SdgItem(
    val iconResId: Int,
    val number: String,
    val subtitle: String,
    val description: String,
    val iconBg: Color,
    val iconTint: Color
)

data class ArtikelItem(
    val imageResId: Int,
    val category: String,
    val title: String,
    val categoryColor: Color,
    val url: String
)

@Composable
fun BookletScreen() {
    val sdgItems = listOf(
        SdgItem(
            iconResId   = R.drawable.ic_sdg_education,
            number      = "SDG 4",
            subtitle    = "Kualitas Edukasi",
            description = "Ensure inclusive and equitable quality education and promote lifelong learning opportunities for all.",
            iconBg      = Color.Transparent,
            iconTint    = OrangeIcon
        ),
        SdgItem(
            iconResId   = R.drawable.ic_sdg_city,
            number      = "SDG 11",
            subtitle    = "Sustainable Cities",
            description = "Make cities and human settlements inclusive, safe, resilient and sustainable transport.",
            iconBg      = Color.Transparent,
            iconTint    = BlueIcon
        ),
    )

    val artikelItems = listOf(
        ArtikelItem(
            imageResId    = R.drawable.img_bus,
            category      = "TRANSPORTASI PUBLIK",
            title         = "Penggunaan angkot umum mengurangi jejak karbon",
            categoryColor = TealPrimary,
            url           = "https://jejakkarbonku.id/berita/393/siapa-sangka-angkot-bisa-jadi-gerbang-menuju-transportasi-rendah-emisi"
        ),
        ArtikelItem(
            imageResId    = R.drawable.img_student,
            category      = "EDUKASI",
            title         = "Akses bepergian bagi masyarakat",
            categoryColor = BlueIcon,
            url           = "https://goodstats.id/article/menggali-tantangan-potensi-transportasi-umum-survei-msib-gnfi-batch-7-dbPgI#google_vignette"
        ),
        ArtikelItem(
            imageResId    = R.drawable.img_city,
            category      = "KOTA CERDAS",
            title         = "Masa depan mobilitas perkotaan hijau",
            categoryColor = OrangeIcon,
            url           = "https://www.councilfire.org/blog/emerging-green-technologies-for-sustainable-urban-development-building-the-cities-of-tomorrow"
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPage)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Top Bar ──
        BookletTopBar()

        Spacer(modifier = Modifier.height(20.dp))

        // ── SDGs Education ──
        SectionTitle(title = "SDGs Education")
        Spacer(modifier = Modifier.height(12.dp))
        SdgHorizontalList(items = sdgItems)

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text       = "Artikel Terbaru",
            fontSize   = 16.sp,
            fontWeight = FontWeight.Bold,
            color      = TextDark,
            modifier   = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        artikelItems.forEach { artikel ->
            ArtikelRow(item = artikel)
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ──────────────────────────────────────────────
// TOP BAR
// ──────────────────────────────────────────────
@Composable
private fun BookletTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text       = "Booklet",
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
            color      = TextDark
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(TealLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Default.Search,
                contentDescription = "Search",
                tint               = TealPrimary,
                modifier           = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text       = title,
        fontSize   = 16.sp,
        fontWeight = FontWeight.Bold,
        color      = TextDark,
        modifier   = Modifier.padding(horizontal = 20.dp)
    )
}

@Composable
private fun SdgHorizontalList(items: List<SdgItem>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEach { item ->
            SdgCard(item = item)
        }
    }
}

@Composable
private fun SdgCard(item: SdgItem) {
    Card(
        modifier  = Modifier.width(220.dp),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(item.iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter            = painterResource(id = item.iconResId),
                        contentDescription = item.number,
                        modifier           = Modifier.size(45.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text       = item.number,
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextDark
                    )
                    Text(
                        text     = item.subtitle,
                        fontSize = 12.sp,
                        color    = TextGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text     = "Deskripsi",
                fontSize = 12.sp,
                color    = TextGray,
                modifier = Modifier.height(60.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick         = { /* TODO: navigate to detail */ },
                modifier        = Modifier
                    .fillMaxWidth()
                    .height(34.dp),
                shape           = RoundedCornerShape(20.dp),
                border          = androidx.compose.foundation.BorderStroke(1.dp, TealPrimary),
                contentPadding  = PaddingValues(0.dp)
            ) {
                Text(
                    text       = "Eksplorasi",
                    fontSize   = 12.sp,
                    color      = TealPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


@Composable
private fun ArtikelRow(item: ArtikelItem) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = item.category,
                    fontSize   = 11.sp,
                    color      = Color(0xFF1CA6A6),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text       = item.title,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextDark,
                    lineHeight = 20.sp,
                    maxLines   = 2,
                    overflow   = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick        = { uriHandler.openUri(item.url) },
                    modifier       = Modifier.height(30.dp),
                    shape          = RoundedCornerShape(20.dp),
                    border         = androidx.compose.foundation.BorderStroke(1.dp, TealPrimary),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp)
                ) {
                    Text(
                        text     = "Baca Artikel",
                        fontSize = 11.sp,
                        color    = TealPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Image(
                painter            = painterResource(id = item.imageResId),
                contentDescription = item.title,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookletScreenPreview() {
    BookletScreen()
}