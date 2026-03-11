package com.example.angkootapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.angkootapp.presentation.welcomePage.WelcomeScreenFourth
import com.example.angkootapp.presentation.welcomePage.WelcomeScreenSec
import com.example.angkootapp.presentation.welcomePage.WelcomeScreenThird
import kotlinx.coroutines.launch

@Composable
fun WelcomeCarouselScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> WelcomeScreenSec(
                    onNextClick = { scope.launch { pagerState.animateScrollToPage(1) } }
                )
                1 -> WelcomeScreenThird(
                    onNextClick = { scope.launch { pagerState.animateScrollToPage(2) } }
                )
                2 -> WelcomeScreenFourth(
                    onNextClick = onFinish
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) { index ->
                val isSelected = pagerState.currentPage == index
                val width = if (isSelected) 24.dp else 6.dp
                val color = if (isSelected) Color(0xFF2CB9D1) else Color(0xFFD9E1E5)

                Box(
                    modifier = Modifier
                        .size(width = width, height = 6.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}