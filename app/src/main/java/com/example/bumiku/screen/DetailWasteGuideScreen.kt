package com.example.bumiku.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.navigation.NavController
import com.example.bumiku.model.WasteCategory
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep

@Composable
fun DetailWasteGuideScreen(category: WasteCategory, navController: NavController) {
    var selectedMaterial by remember { mutableStateOf<Pair<String, String>?>(null) }
    var showAllManagement by remember { mutableStateOf(false) }
    var showAllRecycling by remember { mutableStateOf(false) }

    if (selectedMaterial != null) {
        MaterialScreen(
            category = category,
            title = selectedMaterial!!.first,
            sectionLabel = selectedMaterial!!.second,
            onBack = { selectedMaterial = null }
        )
        return
    }

    if (showAllManagement) {
        AllItemsScreen(
            category = category,
            items = category.managementItems,
            sectionTitle = "Semua Cara Pengelolaan",
            onBack = { showAllManagement = false },
            onItemClick = { name ->
                showAllManagement = false
                selectedMaterial = Pair(name, "Cara Pengelolaan")
            }
        )
        return
    }

    if (showAllRecycling) {
        AllItemsScreen(
            category = category,
            items = category.recyclingTips,
            sectionTitle = "Semua Tips Daur Ulang",
            onBack = { showAllRecycling = false },
            onItemClick = { name ->
                showAllRecycling = false
                selectedMaterial = Pair(name, "Tips Daur Ulang")
            }
        )
        return
    }

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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldYellow,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable { navController.popBackStack() }
                    )
                    Text(
                        text = category.name,
                        color = GoldYellow,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Card(
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        border = BorderStroke(1.dp, BlackSolid.copy(alpha = 0.05f))
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = "Tentang ${category.name}",
                                style = MaterialTheme.typography.titleSmall,
                                color = GreenDeep
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = category.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = BlackSolid.copy(alpha = 0.7f),
                                lineHeight = 20.sp
                            )
                        }
                    }
                }

                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Cara Pengelolaan", style = MaterialTheme.typography.titleMedium, color = BlackSolid)
                        Text(
                            text = "Lihat semua",
                            style = MaterialTheme.typography.labelSmall,
                            color = GreenDeep,
                            modifier = Modifier.clickable { showAllManagement = true }
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        itemsIndexed(category.managementItems) { _, itemName ->
                            ManagementCard(name = itemName, imageRes = category.imageRes) {
                                selectedMaterial = Pair(itemName, "Cara Pengelolaan")
                            }
                        }
                    }
                }

                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Tips Daur Ulang", style = MaterialTheme.typography.titleMedium, color = BlackSolid)
                        Text(
                            text = "Lihat semua",
                            style = MaterialTheme.typography.labelSmall,
                            color = GreenDeep,
                            modifier = Modifier.clickable { showAllRecycling = true }
                        )
                    }
                }

                itemsIndexed(category.recyclingTips) { _, tip ->
                    RecyclingTipCard(title = tip, imageRes = category.imageRes) {
                        selectedMaterial = Pair(tip, "Tips Daur Ulang")
                    }
                }

                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun MaterialScreen(
    category: WasteCategory,
    title: String,
    sectionLabel: String,
    onBack: () -> Unit
) {
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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldYellow,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable { onBack() }
                    )
                    Text(
                        text = title,
                        color = GoldYellow,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(category.imageRes),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(Modifier.padding(16.dp)) {
                Surface(
                    color = GreenDeep,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "${category.icon} ${category.name} · $sectionLabel",
                        color = GoldYellow,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                    color = BlackSolid,
                    lineHeight = 28.sp
                )

                Spacer(Modifier.height(16.dp))

                Card(
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = BorderStroke(1.dp, BlackSolid.copy(alpha = 0.05f))
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Panduan Lengkap",
                            style = MaterialTheme.typography.titleSmall,
                            color = GreenDeep
                        )
                        Text(
                            text = "Pelajari cara mengelola $title sebagai bagian dari sampah ${category.name}. Pengelolaan yang tepat akan membantu mengurangi dampak lingkungan.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = BlackSolid.copy(alpha = 0.7f),
                            lineHeight = 21.sp
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Card(
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = BorderStroke(1.dp, BlackSolid.copy(alpha = 0.05f))
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Langkah-langkah",
                            style = MaterialTheme.typography.titleSmall,
                            color = GreenDeep
                        )
                        listOf(
                            "Kumpulkan $title yang sudah tidak terpakai dan pisahkan dari jenis sampah lainnya.",
                            "Bersihkan dari kotoran atau sisa bahan yang menempel agar proses selanjutnya lebih mudah.",
                            "Pilah berdasarkan kondisi: masih bisa digunakan kembali atau didaur ulang.",
                            "Proses sesuai jenisnya — kompos untuk organik, setor ke bank sampah untuk anorganik.",
                            "Catat dan evaluasi hasil pengelolaan untuk membangun kebiasaan ramah lingkungan."
                        ).forEachIndexed { i, step ->
                            Row(verticalAlignment = Alignment.Top) {
                                Surface(
                                    modifier = Modifier.size(24.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    color = GoldYellow
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "${i + 1}",
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                        )
                                    }
                                }
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    text = step,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = BlackSolid.copy(alpha = 0.7f),
                                    lineHeight = 20.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AllItemsScreen(
    category: WasteCategory,
    items: List<String>,
    sectionTitle: String,
    onBack: () -> Unit,
    onItemClick: (String) -> Unit
) {
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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldYellow,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable { onBack() }
                    )
                    Text(
                        text = sectionTitle,
                        color = GoldYellow,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(items) { _, item ->
                    RecyclingTipCard(title = item, imageRes = category.imageRes) {
                        onItemClick(item)
                    }
                }
            }
        }
    }
}

@Composable
fun ManagementCard(name: String, imageRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(1.dp, BlackSolid.copy(alpha = 0.05f))
    ) {
        Column {
            Image(
                painter = painterResource(imageRes),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(GoldYellow)
                    .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "$name >",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun RecyclingTipCard(title: String, imageRes: Int, onClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(1.dp, BlackSolid.copy(alpha = 0.05f))
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = title,
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = BlackSolid,
                    lineHeight = 18.sp
                )
                Spacer(Modifier.height(6.dp))
                Surface(
                    modifier = Modifier.clickable { onClick() },
                    shape = RoundedCornerShape(20.dp),
                    color = GoldYellow
                ) {
                    Text(
                        text = "Lihat materi",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
