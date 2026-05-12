package com.example.bumiku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val DarkGreen = Color(0xFF1A3C34)
val YellowPrimary = Color(0xFFF5A623)
val LightBg = Color(0xFFF5F5F0)

@Composable
fun TopBar(title: String, onBack: (() -> Unit)?) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(DarkGreen)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(if (onBack != null) YellowPrimary else YellowPrimary.copy(alpha = 0.0f))
                    .then(if (onBack != null) Modifier.clickable { onBack() } else Modifier),
                Alignment.Center
            ) {
                if (onBack != null) {
                    Text("←", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.width(12.dp))
            Text(title, color = YellowPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}