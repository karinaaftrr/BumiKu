package com.example.bumiku.wtrack

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bumiku.model.Task
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.viewmodel.KomunitasViewModel

@Composable
fun TrackerScreen(
    navController: NavController,
    viewModel: KomunitasViewModel
) {

    val allTasks = viewModel.allTasks
    val selectedDay = viewModel.selectedDayInTracker

    val tasksForSelectedDay = allTasks.filter { it.day == selectedDay }

    val totalTasks = tasksForSelectedDay.size
    val doneTasks = tasksForSelectedDay.count { it.isDone }

    val progress = if (totalTasks > 0) {
        doneTasks.toFloat() / totalTasks.toFloat()
    } else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Surface(color = GreenDeep, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = GoldYellow,
                    modifier = Modifier
                        .size(28.dp)
                        .align(Alignment.CenterStart)
                        .clickable { navController.popBackStack() }
                )

                Text(
                    text = "WTracker",
                    color = GoldYellow,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
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

                Spacer(modifier = Modifier.height(20.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {

                    items((1..7).toList()) { day ->

                        DayItem(
                            day = day,
                            isSelected = selectedDay == day,
                            isCompleted = allTasks.filter { it.day == day }.isNotEmpty()
                                    && allTasks.filter { it.day == day }.all { it.isDone },
                            onClick = { viewModel.selectedDayInTracker = day }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                val dayText = when (selectedDay) {
                    1 -> "Pertama"
                    2 -> "Kedua"
                    3 -> "Ketiga"
                    4 -> "Keempat"
                    5 -> "Kelima"
                    6 -> "Keenam"
                    7 -> "Ketujuh"
                    else -> "$selectedDay"
                }

                Text(
                    text = "Daftar Tugas - Hari $dayText",
                    style = MaterialTheme.typography.titleMedium,
                    color = GoldYellow,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            if (tasksForSelectedDay.isEmpty()) {

                item {
                    Text(
                        text = "Belum ada tugas untuk hari ini.",
                        modifier = Modifier.padding(vertical = 20.dp),
                        color = Color.Gray
                    )
                }

            } else {

                items(tasksForSelectedDay, key = { it.id }) { task ->

                    TaskItem(
                        task = task,
                        onCheckedChange = { viewModel.toggleTask(task.id) },
                        onItemClick = {
                            navController.navigate("tracker_detail/${task.id}")
                        }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun DayItem(
    day: Int,
    isSelected: Boolean,
    isCompleted: Boolean,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {

        Text(
            text = "Hari $day",
            fontSize = 12.sp,
            color = if (isSelected) BlackSolid else Color.Gray
        )

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isSelected -> GoldYellow
                        isCompleted -> GreenDeep
                        else -> Color.White
                    }
                )
                .border(
                    1.dp,
                    if (isSelected || isCompleted) Color.Transparent else Color.LightGray,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "H$day",
                fontWeight = FontWeight.Bold,
                color = when {
                    isSelected -> GreenDeep
                    isCompleted -> Color.White
                    else -> Color.Gray
                }
            )
        }
    }
}

@Composable
fun MissionCard(
    doneTasks: Int,
    totalTasks: Int,
    progress: Float
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = GreenDeep)
    ) {

        Column(modifier = Modifier.padding(24.dp)) {

            Text(
                text = "Misi Penyelamatan Bumi",
                color = GoldYellow,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "$doneTasks dari $totalTasks tugas selesai",
                color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

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
                    color = GoldYellow,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: () -> Unit,
    onItemClick: () -> Unit
) {

    val isChecked = task.isDone

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isChecked)
                GoldYellow.copy(alpha = 0.15f)
            else Color(0xFFFFF9E6)
        ),
        border = BorderStroke(
            1.dp,
            if (isChecked) GoldYellow else Color.Black.copy(alpha = 0.1f)
        )
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
                    .background(if (isChecked) GoldYellow else Color.White)
                    .border(2.dp, GoldYellow, RoundedCornerShape(6.dp))
                    .clickable { onCheckedChange() },
                contentAlignment = Alignment.Center
            ) {

                if (isChecked) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = task.title,
                fontWeight = FontWeight.Bold,
                color = GreenDeep,
                modifier = Modifier.weight(1f)
            )

            if (!isChecked) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}