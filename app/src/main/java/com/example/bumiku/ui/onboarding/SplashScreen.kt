package com.example.bumiku.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.ui.theme.Montserrat
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000) // Tampilan splash screen selama 2 detik
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenDeep),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "BumiKu",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Montserrat
        )
    }
}
