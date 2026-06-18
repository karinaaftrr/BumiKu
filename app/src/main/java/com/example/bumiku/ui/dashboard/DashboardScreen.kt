package com.example.bumiku.ui.dashboard

import androidx.compose.foundation.BorderStroke
import coil.compose.AsyncImage
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.bumiku.R
import com.example.bumiku.data.model.Community
import com.example.bumiku.model.Feature
import com.example.bumiku.model.FeatureSource
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.viewmodel.AuthViewModel
import com.example.bumiku.viewmodel.WComViewModel
import com.example.bumiku.viewmodel.WTrackViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun DashboardScreen(
    navController: NavHostController,
    komunitasViewModel: WComViewModel,
    wTrackViewModel: WTrackViewModel,
    authViewModel: AuthViewModel,
    onNotificationClick: () -> Unit = {}
) {
    val user by authViewModel.currentUser.collectAsStateWithLifecycle()
    val listKomunitas by komunitasViewModel.listKomunitas.collectAsStateWithLifecycle()
    val allTasks by wTrackViewModel.allTasks.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    val displayName = remember(user) {
        val name = user?.fullName.orEmpty()

        if (name.isNotBlank() && name != "Pengguna") {
            name
        } else {
            user?.email
                ?.substringBefore("@")
                ?.replaceFirstChar { it.uppercase() }
                ?: "User"
        }
    }

    var isLoading by remember { mutableStateOf(false) }
    var showSearchDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val features = FeatureSource.features

    // Filter fitur khusus untuk bagian Layanan
    val displayServices = remember(features) {
        features.filter {
            it.route in listOf("tracker", "waste_guide", "waste_community", "wnews", "flash_cards")
        }
    }

    val filteredFeatures = features.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    val totalTasks = allTasks.size
    val doneTasks = allTasks.count { it.isDone }

    val habitProgress = if (totalTasks > 0) {
        doneTasks.toFloat() / totalTasks.toFloat()
    } else {
        0f
    }

    val habitPercent = (habitProgress * 100).toInt()

    val joinedEvents = listKomunitas.filter {
        it.isJoined
    }

    val eventProgress = if (listKomunitas.isNotEmpty()) {
        (joinedEvents.size.toFloat() / listKomunitas.size.toFloat())
            .coerceAtMost(1f)
    } else {
        0f
    }

    val nearestEvent = remember(joinedEvents) {
        joinedEvents
            .sortedBy { it.eventTime }
            .firstOrNull()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(top = 12.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
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
                                            delay(300)
                                            isLoading = false
                                            navController.navigate("profil")
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                when {
                                    // Prioritas 1: foto Base64
                                    !user?.profileImageBase64.isNullOrBlank() -> {
                                        val bytes = Base64.decode(user!!.profileImageBase64, Base64.DEFAULT)
                                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                                        Image(
                                            bitmap = bitmap.asImageBitmap(),
                                            contentDescription = "Profile Picture",
                                            modifier = Modifier
                                                .size(52.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                    }

                                    // Prioritas 2: foto URL lama
                                    !user?.profileImageUrl.isNullOrBlank() -> {
                                        AsyncImage(
                                            model = user?.profileImageUrl,
                                            contentDescription = "Profile Picture",
                                            modifier = Modifier
                                                .size(52.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop,
                                            error = painterResource(id = R.drawable.login),
                                            placeholder = painterResource(id = R.drawable.login)
                                        )
                                    }

                                    // Prioritas 3: tidak ada foto → tampil placeholder
                                    else -> {
                                        androidx.compose.material3.Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = null,
                                            modifier = Modifier.size(28.dp),
                                            tint = Color.White
                                        )
                                    }
                                }
                            }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Selamat Datang!",
                            color = BlackSolid,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = displayName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            maxLines = 1,
                            color = GreenDeep,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    IconButton(
                        onClick = {
                            showSearchDialog = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = BlackSolid
                        )
                    }

                    IconButton(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                delay(300)
                                isLoading = false
                                onNotificationClick()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = BlackSolid
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                EcoHabitCard(
                    progressPercent = habitPercent,
                    onClick = {
                        scope.launch {
                            isLoading = true
                            delay(300)
                            isLoading = false
                            navController.navigate("tracker")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Statistik Progress",
                    color = BlackSolid,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ProgressCard(
                        modifier = Modifier.weight(1f),
                        title = "Habit",
                        subtitle = "$doneTasks/$totalTasks tugas",
                        progressValue = habitProgress,
                        progressLabel = "$habitPercent%",
                        color = GoldYellow
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    ProgressCard(
                        modifier = Modifier.weight(1f),
                        title = "Event",
                        subtitle = "Telah Bergabung",
                        progressValue = eventProgress,
                        progressLabel = joinedEvents.size.toString(),
                        color = GoldYellow
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Layanan",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                    color = BlackSolid
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Scroll Horizontal untuk Layanan (Hanya 5 fitur pilihan)
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(end = 16.dp)
                ) {
                    items(displayServices) { feature ->
                        ServiceCard(
                            icon = feature.icon,
                            label = feature.name.split(" ")[0],
                            onClick = {
                                scope.launch {
                                    isLoading = true
                                    delay(300)
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
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 18.sp
                    ),
                    color = BlackSolid
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (nearestEvent == null) {
                    EmptyEventCard()
                } else {
                    EventCard(
                        event = nearestEvent,
                        onClick = {
                            scope.launch {
                                isLoading = true
                                delay(300)
                                isLoading = false
                                navController.navigate("detail_wcom/${nearestEvent.id}")
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(90.dp))
            }
        }

        if (isLoading) {
            LoadingOverlay()
        }
    }

    if (showSearchDialog) {
        SearchDialog(
            searchQuery = searchQuery,
            onQueryChange = {
                searchQuery = it
            },
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
                    delay(300)
                    isLoading = false
                    navController.navigate(route)
                }
            }
        )
    }
}

@Composable
fun EcoHabitCard(
    progressPercent: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = GreenDeep
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = null,
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "WTrack",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Progress Habit $progressPercent%",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Pantau aktivitas baikmu untuk menjaga bumi.",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun ServiceCard(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    GoldYellow.copy(alpha = 0.15f),
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GreenDeep
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = label,
            color = BlackSolid,
            fontSize = 12.sp
        )
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
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
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
                progress = {
                    progressValue
                },
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = progressLabel,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = subtitle,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EmptyEventCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F8)
        ),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = BlackSolid.copy(alpha = 0.05f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = null,
                    tint = BlackSolid.copy(alpha = 0.2f),
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Belum ada event yang diikuti",
                    color = BlackSolid.copy(alpha = 0.4f)
                )

                Text(
                    text = "Yuk bergabung di WComm!",
                    color = GoldYellow
                )
            }
        }
    }
}

@Composable
fun EventCard(
    event: Community,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = BlackSolid.copy(alpha = 0.08f)
        )
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
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = event.judul,
                    fontWeight = FontWeight.Bold,
                    color = BlackSolid,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${event.tanggal} | ${event.lokasi}",
                    color = GoldYellow,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = GoldYellow
            )
        }
    }
}

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black.copy(alpha = 0.4f)
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = GoldYellow
        )
    }
}

@Composable
fun SearchDialog(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    filteredFeatures: List<Feature>,
    onDismiss: () -> Unit,
    onFeatureClick: (String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Cari fitur...")
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(filteredFeatures) { feature ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onFeatureClick(feature.route)
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = feature.icon,
                                contentDescription = null,
                                tint = GreenDeep
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = feature.name,
                                color = BlackSolid
                            )
                        }
                    }

                    if (filteredFeatures.isEmpty()) {
                        item {
                            Text(
                                text = "Fitur tidak ditemukan",
                                modifier = Modifier.padding(12.dp),
                                color = BlackSolid.copy(alpha = 0.5f)
                            )
                        }
                    }

                    item {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Tutup",
                                color = GreenDeep
                            )
                        }
                    }
                }
            }
        }
    }
}
