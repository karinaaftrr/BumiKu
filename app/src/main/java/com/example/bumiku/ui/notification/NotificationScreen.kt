package com.example.bumiku.ui.notification

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bumiku.data.model.Notification
import com.example.bumiku.data.repository.NotificationRepository
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep

@Composable
fun NotificationScreen(
    onBackClick: () -> Unit
) {
    var notifications by remember {
        mutableStateOf<List<Notification>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null

        val data = NotificationRepository().getNotifications()
        notifications = data

        if (data.isEmpty()) {
            errorMessage = "Data notifikasi gagal dimuat. Cek koneksi internet kamu."
        }

        isLoading = false
    }

    Scaffold(
        containerColor = Color.White,
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
                        contentDescription = "Kembali",
                        tint = GoldYellow,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable {
                                onBackClick()
                            }
                    )

                    Text(
                        text = "Notifikasi",
                        style = MaterialTheme.typography.titleLarge,
                        color = GoldYellow,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    ) { paddingValues ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = GoldYellow)
                }
            }

            errorMessage != null -> {
                ErrorNotificationCard(
                    modifier = Modifier.padding(paddingValues),
                    message = errorMessage ?: "Data gagal dimuat."
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color.White)
                ) {
                    items(
                        items = notifications,
                        key = { it.id }
                    ) { notification ->
                        NotificationCard(notification)
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationCard(
    notification: Notification
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = GoldYellow.copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = GoldYellow,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = notification.time,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ErrorNotificationCard(
    modifier: Modifier = Modifier,
    message: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE)
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(16.dp),
            color = Color(0xFFC62828),
            fontWeight = FontWeight.SemiBold
        )
    }
}