package com.example.bumiku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.ui.theme.BlackSolid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanduanScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panduan Penggunaan", color = GoldYellow, fontWeight = FontWeight.Bold) },
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
                .padding(20.dp)
        ) {
            Text(
                text = "Cara Menggunakan BumiKu",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = GreenDeep
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            PanduanItem(
                step = "1",
                title = "Dashboard",
                description = "Lihat ringkasan aktivitas harianmu, statistik progress, dan event lingkungan terdekat di halaman utama."
            )
            PanduanItem(
                step = "2",
                title = "WTrack (Tracker)",
                description = "Catat aktivitas ramah lingkunganmu setiap hari untuk membangun kebiasaan baik dan mendapatkan streak."
            )
            PanduanItem(
                step = "3",
                title = "WGuide (Edukasi)",
                description = "Pelajari cara pengelolaan sampah yang benar melalui panduan literasi lingkungan yang lengkap."
            )
            PanduanItem(
                step = "4",
                title = "WComm (Komunitas)",
                description = "Temukan dan bergabunglah dengan komunitas lingkungan atau ikuti event aksi nyata di sekitarmu."
            )
            PanduanItem(
                step = "5",
                title = "WNews (Berita)",
                description = "Dapatkan informasi terbaru mengenai isu lingkungan dan tips keberlanjutan setiap harinya."
            )
        }
    }
}

@Composable
fun PanduanItem(step: String, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Surface(
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                color = GoldYellow
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = step, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, color = BlackSolid, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = description, color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}
