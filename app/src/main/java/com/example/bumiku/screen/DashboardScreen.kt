package com.example.bumiku.screen

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.bumiku.R
import com.example.bumiku.model.FeatureSource
import com.example.bumiku.model.Task
import com.example.bumiku.model.TaskSource
import com.example.bumiku.ui.theme.*
import com.example.bumiku.viewmodel.KomunitasViewModel
import kotlinx.coroutines.delay

@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: KomunitasViewModel,
    onNotificationClick: () -> Unit = {}
) {
    var activeTasks by remember {
        mutableStateOf(TaskSource.dummyTasks.filter { !it.isDone })
    }
    var tasksCompletedOnce by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showSearchDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val features = FeatureSource.features

    val filteredFeatures = features.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    val nearestEvent = viewModel.listKomunitas.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Surface(color = GreenDeep, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Dashboard",
                    color = GoldYellow,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable { navController.navigate("profil") }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profil),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1.0f)) {
                    Text(text = "Selamat Datang!", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(
                        text = "Tiwi Mustika Dewi",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp, lineHeight = 28.sp),
                        color = BlackSolid
                    )
                }
                IconButton(onClick = { showSearchDialog = true }) {
                    Icon(Icons.Outlined.Search, contentDescription = "Search", tint = BlackSolid)
                }
                IconButton(onClick = onNotificationClick) {
                    Icon(Icons.Outlined.Notifications, contentDescription = null, tint = BlackSolid)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (activeTasks.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(activeTasks, key = { it.id }) { task ->
                        var isVisible by remember { mutableStateOf(true) }

                        AnimatedVisibility(
                            visible = isVisible,
                            exit = fadeOut() + shrinkHorizontally()
                        ) {
                            HabitCard(
                                task = task,
                                onClose = { isVisible = false },
                                onDone = { isVisible = false }
                            )
                        }

                        LaunchedEffect(isVisible) {
                            if (!isVisible) {
                                delay(300)
                                activeTasks = activeTasks.filter { it.id != task.id }
                                if (activeTasks.isEmpty()) {
                                    tasksCompletedOnce = true
                                    showSuccessDialog = true
                                }
                            }
                        }
                    }
                }
            } else if (tasksCompletedOnce) {
                CongratsCard()
            }

            if (showSuccessDialog) {
                SuccessDialog(onDismiss = { showSuccessDialog = false })
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Statistik Progress",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                color = BlackSolid,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                ProgressCard(
                    modifier = Modifier.weight(1f),
                    title = "Habit Selesai",
                    subtitle = "Hari ini",
                    progressValue = 0.6f,
                    progressLabel = "60%",
                    color = GoldYellow
                )
                Spacer(modifier = Modifier.width(16.dp))
                ProgressCard(
                    modifier = Modifier.weight(1f),
                    title = "Event Selesai",
                    subtitle = "21 Maret - 21 April",
                    progressValue = 0.8f,
                    progressLabel = "2",
                    color = GoldYellow
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Layanan", style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp), color = BlackSolid)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                features.take(4).forEach { feature ->
                    ServiceItem(
                        icon = feature.icon,
                        label = feature.name.split(" ")[0],
                        onClick = { navController.navigate(feature.route) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Event Terdekat", style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp), color = BlackSolid)
            Spacer(modifier = Modifier.height(12.dp))

            nearestEvent?.let { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("detail/${event.judul}") },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, BlackSolid.copy(alpha = 0.05f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(GoldYellow, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = event.judul,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                                color = BlackSolid
                            )
                            Text(
                                text = "${event.tanggal} || ${event.lokasi}",
                                style = MaterialTheme.typography.labelSmall,
                                color = GoldYellow
                            )
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = GoldYellow)
                    }
                }
            }
        }
    }

    if (showSearchDialog) {
        Dialog(onDismissRequest = {
            showSearchDialog = false
            searchQuery = ""
        }) {
            Card(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Cari Fitur", style = MaterialTheme.typography.titleLarge, color = GreenDeep)
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ketik nama fitur...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GreenDeep,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (searchQuery.isEmpty()) {
                        Text(text = "Coba cari: WTrack, WGuide, WNews, atau WComm", style = MaterialTheme.typography.bodySmall, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(filteredFeatures) { feature ->
                                Card(
                                    modifier = Modifier.fillMaxWidth().clickable { showSearchDialog = false; navController.navigate(feature.route) },
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Icon(feature.icon, contentDescription = null, tint = GreenDeep)
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(text = feature.name, style = MaterialTheme.typography.bodyLarge, color = BlackSolid)
                                    }
                                }
                            }
                            if (filteredFeatures.isEmpty()) {
                                item {
                                    Box(modifier = Modifier.fillMaxSize().padding(top = 32.dp), contentAlignment = Alignment.Center) {
                                        Text("Fitur tidak ditemukan", color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HabitCard(task: Task, onClose: () -> Unit, onDone: () -> Unit) {
    Card(
        modifier = Modifier.width(280.dp),
        colors = CardDefaults.cardColors(containerColor = GreenDeep),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.BarChart, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "WTrack", style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp), color = Color.White)
                }
                IconButton(onClick = onClose, modifier = Modifier.size(28.dp).background(Color.White.copy(alpha = 0.2f), CircleShape)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White, modifier = Modifier.size(14.dp))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = task.title, style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp), color = Color.White.copy(alpha = 0.8f), minLines = 2)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onDone, modifier = Modifier.align(Alignment.End), colors = ButtonDefaults.buttonColors(containerColor = GoldYellow), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), shape = RoundedCornerShape(20.dp)) {
                Text(text = "Selesai >", style = MaterialTheme.typography.labelSmall, color = Color.White)
            }
        }
    }
}

@Composable
fun CongratsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = GreenDeep),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Misi Selesai!", style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp), color = Color.White)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Selamat karena sudah menyelesaikan misi harian! Ayo terus semangat berkontribusi menjaga lingkungan setiap hari.", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.9f), lineHeight = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Bumi bangga padamu! 🌍✨", style = MaterialTheme.typography.labelSmall, color = GoldYellow, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onDismiss, modifier = Modifier.align(Alignment.TopEnd).padding(12.dp).size(28.dp).background(Color.Black.copy(alpha = 0.05f), CircleShape)) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray, modifier = Modifier.size(14.dp))
                }
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🎉", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Good Job!", style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp), color = GreenDeep)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Kamu telah menyelesaikan semua tugas hari ini. Terus jaga bumi kita!", style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center, color = BlackSolid.copy(alpha = 0.7f))
                    Spacer(modifier = Modifier.height(24.dp))
                    Box(modifier = Modifier.fillMaxWidth().background(GoldYellow, RoundedCornerShape(12.dp)).padding(vertical = 12.dp), contentAlignment = Alignment.Center) {
                        Text("Mantap!", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun ProgressCard(modifier: Modifier = Modifier, title: String, subtitle: String, progressValue: Float, progressLabel: String, color: Color) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = color), shape = RoundedCornerShape(24.dp)) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp)) {
                CircularProgressIndicator(progress = { progressValue }, modifier = Modifier.fillMaxSize(), color = Color.White, strokeWidth = 8.dp, trackColor = Color.White.copy(alpha = 0.3f)
                )
                Text(text = progressLabel, style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp), color = Color.White, textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = title, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp), color = Color.White, textAlign = TextAlign.Center)
            Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.8f), textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ServiceItem(icon: ImageVector, label: String, isSelected: Boolean = false, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Box(modifier = Modifier.size(64.dp).background(if (isSelected) GreenDeep else GoldYellow.copy(alpha = 0.15f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
            Icon(imageVector = icon, contentDescription = null, tint = if (isSelected) Color.White else GreenDeep, modifier = Modifier.size(30.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = BlackSolid)
    }
}
