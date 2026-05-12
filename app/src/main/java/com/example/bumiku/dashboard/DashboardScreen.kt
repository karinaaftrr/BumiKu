package com.example.bumiku.dashboard

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.bumiku.R
import com.example.bumiku.model.Feature
import com.example.bumiku.model.FeatureSource
import com.example.bumiku.model.Task
import com.example.bumiku.ui.theme.*
import com.example.bumiku.viewmodel.AuthViewModel
import com.example.bumiku.viewmodel.KomunitasViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.bumiku.navigation.Screen

@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: KomunitasViewModel,
    authViewModel: AuthViewModel,
    onNotificationClick: () -> Unit = {}
) {
    val user = authViewModel.currentUser
    val scope = rememberCoroutineScope()

    var isLoading by remember { mutableStateOf(false) }
    var tasksCompletedOnce by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showSearchDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val activeTasks = viewModel.allTasks.filter { !it.isDone }
    val features = FeatureSource.features
    val filteredFeatures = features.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }
    val nearestEvent = viewModel.listKomunitas.firstOrNull { it.isJoined }

    LaunchedEffect(activeTasks.size) {
        if (activeTasks.isEmpty() && viewModel.allTasks.any { it.isDone }) {
            tasksCompletedOnce = true
            showSuccessDialog = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(top = 12.dp)
        ) {

            Column(modifier = Modifier.padding(20.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .clickable {
                                scope.launch {
                                    isLoading = true
                                    delay(600)
                                    isLoading = false
                                    navController.navigate("profil")
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = user?.profileImage ?: R.drawable.login),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Selamat Datang!", color = BlackSolid, fontSize = 16.sp)
                        Text(
                            user?.fullName ?: "User",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            maxLines = 1,
                            color = BlackSolid,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))


                    IconButton(onClick = { showSearchDialog = true }) {
                        Icon(Icons.Outlined.Search, contentDescription = null, tint = BlackSolid)
                    }

                    IconButton(onClick = {
                        scope.launch {
                            isLoading = true
                            delay(1200)
                            isLoading = false
                            onNotificationClick()
                        }
                    }) {
                        Icon(Icons.Outlined.Notifications, contentDescription = null, tint = BlackSolid)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (activeTasks.isNotEmpty()) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(activeTasks) { task ->
                            HabitCard(
                                task = task,
                                onDone = {
                                    scope.launch {
                                        isLoading = true
                                        delay(800)
                                        viewModel.completeTask(task.id)
                                        isLoading = false
                                    }
                                }
                            )
                        }
                    }
                } else if (tasksCompletedOnce) {
                    CongratsCard()
                }

                if (showSuccessDialog) {
                    SuccessDialog { showSuccessDialog = false }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Statistik Progress",
                    color = BlackSolid,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                val habitProgress =
                    if (viewModel.allTasks.isNotEmpty()) {
                        viewModel.allTasks.count { it.isDone }.toFloat() / viewModel.allTasks.size
                    } else 0f

                val joinedEvents = viewModel.listKomunitas.filter { it.isJoined }

                val eventProgress =
                    if (viewModel.listKomunitas.isNotEmpty()) {
                        (joinedEvents.size.toFloat() / viewModel.listKomunitas.size.toFloat())
                            .coerceAtMost(1f)
                    } else 0f

                Row(modifier = Modifier.fillMaxWidth()) {

                    ProgressCard(
                        modifier = Modifier.weight(1f),
                        title = "Habit",
                        subtitle = "Hari ini",
                        progressValue = habitProgress,
                        progressLabel = "${(habitProgress * 100).toInt()}%",
                        color = GoldYellow
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    ProgressCard(
                        modifier = Modifier.weight(1f),
                        title = "Event",
                        subtitle = "Telah Bergabung",
                        progressValue = eventProgress,
                        progressLabel = joinedEvents.size.toString(),
                        color = GoldYellow,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Layanan",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                    color = BlackSolid
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    features.take(4).forEach { feature ->
                        ServiceCard(
                            icon = feature.icon,
                            label = feature.name.split(" ")[0],
                            onClick = {
                                scope.launch {
                                    isLoading = true
                                    delay(1000)
                                    isLoading = false
                                    navController.navigate(feature.route)
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


                Text(
                    text = "Event Terdekat",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                    color = BlackSolid
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (nearestEvent == null) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, BlackSolid.copy(alpha = 0.05f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Default.Event,
                                    contentDescription = null,
                                    tint = BlackSolid.copy(alpha = 0.2f),
                                    modifier = Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Belum ada event yang diikuti",
                                    color = BlackSolid.copy(alpha = 0.4f)
                                )
                                Text(
                                    "Yuk bergabung di WComm!",
                                    color = GoldYellow
                                )
                            }
                        }
                    }

                } else {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch {
                                    isLoading = true
                                    delay(1000)
                                    isLoading = false
                                    navController.navigate("detail/${nearestEvent.judul}")
                                }
                            },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, BlackSolid.copy(alpha = 0.08f))
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
                                Icon(Icons.Default.LocationOn, null, tint = Color.White)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    nearestEvent.judul,
                                    fontWeight = FontWeight.Bold,
                                    color = BlackSolid
                                )
                                Text(
                                    "${nearestEvent.tanggal} | ${nearestEvent.lokasi}",
                                    color = GoldYellow,
                                    fontSize = 14.sp
                                )
                            }

                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = GoldYellow
                            )
                        }
                    }
                }
            }
        }

        if (isLoading) {
            LoadingOverlay()
        }
    }

    if (showSearchDialog) {
        SearchDialog(
            searchQuery = searchQuery,
            onQueryChange = { searchQuery = it },
            filteredFeatures = filteredFeatures,
            onDismiss = {
                showSearchDialog = false
                searchQuery = ""
            },
            onFeatureClick = { route ->
                scope.launch {
                    showSearchDialog = false
                    searchQuery = ""
                    isLoading = true
                    delay(1000)
                    isLoading = false
                    navController.navigate(route)
                }
            }
        )
    }
}

@Composable
fun ServiceCard(icon: ImageVector, label: String, onClick: () -> Unit, style: TextStyle = MaterialTheme.typography.bodyMedium) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(GoldYellow.copy(alpha = 0.15f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = GreenDeep)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(label, color = BlackSolid, fontSize = 12.sp)
    }
}

@Composable
fun HabitCard(task: Task, onDone: () -> Unit, style: TextStyle = MaterialTheme.typography.bodyMedium) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .height(180.dp),
        colors = CardDefaults.cardColors(containerColor = GreenDeep),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.BarChart, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "WTrack",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(task.title, color = Color.White)

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onDone,
                colors = ButtonDefaults.buttonColors(containerColor = GoldYellow)
            ) {
                Text("Selesai", color = Color.White)
            }
        }
    }
}

@Composable
fun ProgressCard(
    modifier: Modifier,
    title: String,
    subtitle: String,
    progressValue: Float,
    progressLabel: String,
    color: Color,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                progress = { progressValue },
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                progressLabel,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                subtitle,
                color = Color.White,
                textAlign = TextAlign.Center
            )
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
            Text("Misi Selesai!", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Selamat! Kamu telah menjaga bumi hari ini.", color = Color.White)
        }
    }
}

@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Good Job!", color = GreenDeep, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Semua tugas selesai!", textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismiss) {
                    Text("OK")
                }
            }
        }
    }
}

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = GoldYellow)
    }
}

@Composable
fun SearchDialog(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    filteredFeatures: List<Feature>,
    onDismiss: () -> Unit,
    onFeatureClick: (String) -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Cari fitur...") }
                )

                LazyColumn {
                    items(filteredFeatures) { feature ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onFeatureClick(feature.route) }
                                .padding(12.dp)
                        ) {
                            Text(feature.name)
                        }
                    }
                }
            }
        }
    }
}
