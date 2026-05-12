package com.example.bumiku.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WasteCommunity(
    navController: NavHostController,
    viewModel: KomunitasViewModel
) {
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    val listTampil = viewModel.filteredKomunitas

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderSection(
                onBackClick = { navController.popBackStack() },
                onHistoryClick = { navController.navigate("history_wcom") }
            )
            
            FilterCategorySection(
                selectedCategory = viewModel.selectedCategory,
                onCategorySelected = { viewModel.selectedCategory = it }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(listTampil, key = { it.judul }) { komunitas ->
                    CardKomunitas(
                        komunitas = komunitas,
                        onJoinClick = {
                            scope.launch {
                                isLoading = true
                                viewModel.tambahPartisipan(komunitas.judul)
                                delay(500)
                                isLoading = false
                                navController.navigate("detail/${komunitas.judul}")
                            }
                        }
                    )
                }
            }
        }
        if (isLoading) LoadingOverlay()
    }
}

@Composable
fun CardKomunitas(komunitas: Komunitas, onJoinClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = GoldYellow),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                Image(
                    painter = painterResource(id = komunitas.gambar),
                    contentDescription = komunitas.judul,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    color = GreenDeep,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = komunitas.kategori,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = GoldYellow,
                        fontSize = 11.sp
                    )
                }
            }
            Surface(modifier = Modifier.fillMaxWidth(), color = Color.White) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = komunitas.judul,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = BlackSolid
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow(android.R.drawable.ic_menu_my_calendar, komunitas.tanggal)
                    InfoRow(android.R.drawable.ic_menu_mylocation, komunitas.lokasi)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Partisipan:", fontSize = 10.sp, color = BlackSolid.copy(alpha = 0.5f))
                            Text(
                                text = "${komunitas.slotTerisi}/${komunitas.totalSlot}",
                                color = Color.Red,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp
                            )
                        }
                        Button(
                            onClick = onJoinClick,
                            enabled = komunitas.slotTerisi < komunitas.totalSlot,
                            colors = ButtonDefaults.buttonColors(containerColor = GreenDeep),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(text = "Partisipasi", color = GoldYellow)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderSection(onBackClick: () -> Unit, onHistoryClick: () -> Unit) {
    Surface(color = GreenDeep, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = GoldYellow,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterStart)
                    .clickable { onBackClick() }
            )
            Text(
                text = "WComm",
                color = GoldYellow,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "History",
                tint = GoldYellow,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterEnd)
                    .clickable { onHistoryClick() }
            )
        }
    }
}

@Composable
fun FilterCategorySection(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val categories = listOf("Semua", "Daur Ulang", "Bersih-Bersih", "Edukasi", "Tanam Pohon")
    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(categories) { category ->
            val isSelected = category == selectedCategory
            Surface(
                onClick = { onCategorySelected(category) },
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) GreenDeep else Color.Transparent,
                border = if (isSelected) null else BorderStroke(1.dp, GoldYellow)
            ) {
                Text(category, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), color = if (isSelected) GoldYellow else GreenDeep)
            }
        }
    }
}

@Composable
fun InfoRow(iconRes: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
        Icon(painterResource(iconRes), null, modifier = Modifier.size(14.dp), tint = GreenDeep)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 12.sp, color = BlackSolid.copy(alpha = 0.7f))
    }
}

@Composable
fun LoadingOverlay() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(0.4f)), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = GoldYellow)
    }
}
