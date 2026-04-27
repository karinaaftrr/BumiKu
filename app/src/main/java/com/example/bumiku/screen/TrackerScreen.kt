package com.example.bumiku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bumiku.model.Task
import com.example.bumiku.model.TaskSource
import com.example.bumiku.ui.theme.*

@Composable
fun TrackerScreen(navController: NavController) {
    val tasks = remember { 
        mutableStateListOf<Task>().apply {
            addAll(TaskSource.dummyTasks)
        }
    }
    val totalTasks = tasks.size
    val doneTasks = tasks.count { it.isDone }
    val progress = if (totalTasks > 0) doneTasks.toFloat() / totalTasks.toFloat() else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
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
                    text = "WTrack",
                    color = GoldYellow,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                MissionCard(
                    doneTasks = doneTasks,
                    totalTasks = totalTasks,
                    progress = progress
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Daftar Tugas",
                    style = MaterialTheme.typography.titleMedium,
                    color = BlackSolid
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onCheckedChange = {
                        val index = tasks.indexOfFirst { it.id == task.id }
                        if (index != -1) {
                            tasks[index] = tasks[index].copy(isDone = !tasks[index].isDone)
                        }
                    },
                    onItemClick = {
                        navController.navigate("tracker_detail/${task.id}")
                    }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun MissionCard(doneTasks: Int, totalTasks: Int, progress: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = GreenDeep),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Misi\nPenyelamatan\nBumi",
                style = MaterialTheme.typography.titleLarge,
                color = GoldYellow,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$doneTasks dari $totalTasks tugas selesai",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .weight(1f)
                        .height(10.dp)
                        .clip(CircleShape),
                    color = GoldYellow,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelMedium,
                    color = GoldYellow
                )
            }
        }
    }
}

@Composable
private fun TaskItem(
    task: Task,
    onCheckedChange: () -> Unit,
    onItemClick: () -> Unit
) {
    val isChecked = task.isDone
    val bgColor = if (isChecked) GoldYellow.copy(alpha = 0.1f) else Color.White

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isChecked) GoldYellow else BlackSolid.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (isChecked) GoldYellow else Color.Transparent)
                    .border(
                        width = 2.dp,
                        color = GoldYellow,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable { onCheckedChange() },
                contentAlignment = Alignment.Center
            ) {
                if (isChecked) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = BlackSolid,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = BlackSolid,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
