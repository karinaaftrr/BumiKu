package com.example.bumiku.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bumiku.model.Komunitas
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.viewmodel.KomunitasViewModel

@Composable
fun HistoryWCom(
    navController: NavHostController,
    viewModel: KomunitasViewModel
) {
    val joinedKomunitas = viewModel.listKomunitas.filter { it.isJoined }

    Scaffold(
        topBar = {
            Surface(color = GreenDeep, modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldYellow,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable { navController.popBackStack() }
                    )
                    Text(
                        text = "History",
                        color = GoldYellow,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            if (joinedKomunitas.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Belum ada partisipasi aktif", color = BlackSolid.copy(alpha = 0.5f))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(joinedKomunitas) { komunitas ->
                        HistoryCard(
                            komunitas = komunitas,
                            onClick = {
                                navController.navigate("detail/${komunitas.judul}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryCard(komunitas: Komunitas, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BlackSolid.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = komunitas.gambar),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    color = GreenDeep,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = komunitas.kategori,
                        color = GoldYellow,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = komunitas.judul,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = BlackSolid
                )
                Text(
                    text = komunitas.tanggal,
                    fontSize = 11.sp,
                    color = BlackSolid.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(androidx.compose.foundation.shape.CircleShape).background(GoldYellow))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Partisipasi Aktif",
                        color = GreenDeep,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
