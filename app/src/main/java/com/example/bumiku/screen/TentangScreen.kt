package com.example.bumiku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.ui.theme.BlackSolid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TentangScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tentang BumiKu", color = GoldYellow, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = GoldYellow)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreenDeep)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(100.dp),
                shape = RoundedCornerShape(24.dp),
                color = GreenDeep.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "🌍", fontSize = 48.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "BumiKu",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = GreenDeep,
                fontSize = 19.sp
            )
            Text(
                text = "Versi 1.0.0",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Deskripsi Proyek",
                        fontWeight = FontWeight.Bold,
                        color = GreenDeep,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Proyek aplikasi BumiKu ini disusun sebagai proyek untuk tugas mata kuliah Teknologi dan Aplikasi Mobile.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = BlackSolid,
                        textAlign = TextAlign.Justify,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Visi Tim NyawIT:",
                        fontWeight = FontWeight.Bold,
                        color = GreenDeep,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Mendukung SDG 13 (Climate Action) dengan meningkatkan kesadaran masyarakat akan pentingnya menjaga lingkungan melalui teknologi mobile yang inklusif.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = BlackSolid,
                        textAlign = TextAlign.Justify,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "© 2024 Tim NyawIT",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}
