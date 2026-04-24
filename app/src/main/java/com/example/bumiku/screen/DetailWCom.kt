package com.example.bumiku.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun DetailWCom(
    komunitas: Komunitas,
    navController: NavHostController,
    viewModel: KomunitasViewModel
) {
    val currentKomunitas = viewModel.listKomunitas.find { it.judul == komunitas.judul } ?: komunitas

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
                        text = "WComm",
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
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, BlackSolid.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = currentKomunitas.gambar),
                            contentDescription = currentKomunitas.judul,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Surface(
                            color = GreenDeep,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = currentKomunitas.kategori,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                color = GoldYellow,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = currentKomunitas.judul,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlackSolid
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        DetailInfoItem(android.R.drawable.ic_menu_my_calendar, currentKomunitas.tanggal)
                        DetailInfoItem(android.R.drawable.ic_menu_mylocation, currentKomunitas.lokasi)
                        DetailInfoItem(android.R.drawable.ic_menu_view, currentKomunitas.penyelenggara)
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, GreenDeep),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Partisipasi", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = BlackSolid)
                    Spacer(modifier = Modifier.width(8.dp))
                    LinearProgressIndicator(
                        progress = { currentKomunitas.slotTerisi.toFloat() / currentKomunitas.totalSlot.toFloat() },
                        modifier = Modifier.weight(1f).height(10.dp).clip(CircleShape),
                        color = Color.Red,
                        trackColor = BlackSolid.copy(alpha = 0.1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${currentKomunitas.slotTerisi}/${currentKomunitas.totalSlot} slot",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                Text("Tentang Kegiatan", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = BlackSolid)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Kegiatan bersih-bersih pantai bersama komunitas peduli lingkungan. Peserta diharapkan membawa semangat dan siap menjaga kebersihan pesisir Lampung.",
                    fontSize = 13.sp,
                    color = BlackSolid.copy(alpha = 0.6f),
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text("Penyelenggara", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = BlackSolid)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 10.dp)) {
                    Surface(modifier = Modifier.size(42.dp), shape = CircleShape, color = GreenDeep) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("GH", color = GoldYellow, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(currentKomunitas.penyelenggara, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = GreenDeep)
                        Text("Verified Community", fontSize = 12.sp, color = BlackSolid.copy(alpha = 0.5f))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Kamu Sudah Terdaftar", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = BlackSolid)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                    Box(modifier = Modifier.size(14.dp).clip(CircleShape).background(GoldYellow))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Partisipasi Aktif", color = GreenDeep, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        viewModel.kurangiPartisipan(currentKomunitas.judul)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Batalkan", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun DetailInfoItem(iconRes: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(18.dp), tint = BlackSolid.copy(alpha = 0.4f))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, fontSize = 13.sp, color = BlackSolid.copy(alpha = 0.6f))
    }
}
