package com.example.bumiku.ui.wcom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.bumiku.data.model.Community
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.viewmodel.WComViewModel

@Composable
fun DetailWCom(
    komunitas: Community,
    navController: NavHostController,
    viewModel: WComViewModel
) {
    val listKomunitas by viewModel.listKomunitas.collectAsStateWithLifecycle()
    val isUpdating by viewModel.isUpdating.collectAsStateWithLifecycle()

    val currentKomunitas =
        listKomunitas.find { it.id == komunitas.id } ?: komunitas

    val sisaSlot =
        (currentKomunitas.totalSlot - currentKomunitas.slotTerisi).coerceAtLeast(0)

    Scaffold(
        topBar = {
            Surface(
                color = GreenDeep,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldYellow,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable {
                                navController.popBackStack()
                            }
                    )

                    Text(
                        text = "WComm",
                        color = GoldYellow,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
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
                        .border(
                            width = 1.dp,
                            color = BlackSolid.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            Image(
                                painter = painterResource(
                                    id = getCommunityImage(currentKomunitas.imageName)
                                ),
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
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    ),
                                    color = GoldYellow,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = currentKomunitas.judul,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = BlackSolid
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            DetailInfoItem(
                                iconRes = android.R.drawable.ic_menu_my_calendar,
                                text = currentKomunitas.tanggal
                            )

                            DetailInfoItem(
                                iconRes = android.R.drawable.ic_menu_mylocation,
                                text = currentKomunitas.lokasi
                            )

                            DetailInfoItem(
                                iconRes = android.R.drawable.ic_menu_view,
                                text = currentKomunitas.penyelenggara
                            )
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
                        Text(
                            text = "Sisa Slot",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = BlackSolid
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        LinearProgressIndicator(
                            progress = {
                                if (currentKomunitas.totalSlot == 0) {
                                    0f
                                } else {
                                    currentKomunitas.slotTerisi.toFloat() /
                                            currentKomunitas.totalSlot.toFloat()
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(10.dp)
                                .clip(CircleShape),
                            color = Color.Red,
                            trackColor = BlackSolid.copy(alpha = 0.1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "$sisaSlot/${currentKomunitas.totalSlot}",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Tentang Kegiatan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = BlackSolid
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Kegiatan ini mengajak peserta untuk ikut menjaga lingkungan melalui aksi nyata bersama komunitas peduli bumi.",
                        fontSize = 13.sp,
                        color = BlackSolid.copy(alpha = 0.6f),
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Penyelenggara",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = BlackSolid
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(42.dp),
                            shape = CircleShape,
                            color = GreenDeep
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = currentKomunitas.penyelenggara.take(2).uppercase(),
                                    color = GoldYellow,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = currentKomunitas.penyelenggara,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = GreenDeep
                            )

                            Text(
                                text = "Verified Community",
                                fontSize = 12.sp,
                                color = BlackSolid.copy(alpha = 0.5f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    if (currentKomunitas.isJoined) {
                        Text(
                            text = "Kamu Sudah Terdaftar",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = BlackSolid
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                viewModel.batalkanKegiatan(
                                    communityId = currentKomunitas.id
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            ),
                            shape = RoundedCornerShape(14.dp),
                            enabled = !isUpdating
                        ) {
                            if (isUpdating) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text(
                                    text = "Batalkan Partisipasi",
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 17.sp
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "Kamu belum terdaftar di kegiatan ini.",
                            color = BlackSolid.copy(alpha = 0.6f),
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            if (isUpdating) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = GoldYellow)
                }
            }
        }
    }
}

@Composable
fun DetailInfoItem(
    iconRes: Int,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = BlackSolid.copy(alpha = 0.4f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 13.sp,
            color = BlackSolid.copy(alpha = 0.6f)
        )
    }
}