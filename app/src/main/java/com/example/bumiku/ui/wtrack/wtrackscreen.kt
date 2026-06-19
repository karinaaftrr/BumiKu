package com.example.bumiku.ui.wtrack

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bumiku.data.model.Task
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.viewmodel.WTrackViewModel

@Composable
fun WTrackScreen(
    navController: NavController,
    viewModel: WTrackViewModel
) {
    val allTasks by viewModel.allTasks.collectAsStateWithLifecycle()
    val selectedDay by viewModel.selectedDay.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isUpdating by viewModel.isUpdating.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    val tasksForSelectedDay = allTasks.filter {
        it.day == selectedDay
    }

    val totalTasks = tasksForSelectedDay.size
    val doneTasks = tasksForSelectedDay.count {
        it.isDone
    }

    val progress = if (totalTasks > 0) {
        doneTasks.toFloat() / totalTasks.toFloat()
    } else {
        0f
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
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
                        contentDescription = null,
                        tint = GoldYellow,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable {
                                navController.popBackStack()
                            }
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

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = GoldYellow)
                    }
                }

                errorMessage != null && allTasks.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        ErrorMessageCard(
                            message = errorMessage ?: "Data task tidak dapat dimuat."
                        )
                    }
                }

                allTasks.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Belum ada task.",
                            color = BlackSolid.copy(alpha = 0.5f)
                        )
                    }
                }

                else -> {
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
                                    val dayTasks = allTasks.filter {
                                        it.day == day
                                    }

                                    DayItem(
                                        day = day,
                                        isSelected = selectedDay == day,
                                        isCompleted = dayTasks.isNotEmpty() && dayTasks.all { it.isDone },
                                        onClick = {
                                            viewModel.selectDay(day)
                                        }
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
                            items(
                                items = tasksForSelectedDay,
                                key = { it.id }
                            ) { task ->
                                TaskItem(
                                    task = task,
                                    isUpdating = isUpdating,
                                    onCheckedChange = {
                                        viewModel.toggleTask(task.id)
                                    },
                                    onItemClick = {
                                        navController.navigate("tracker_detail/${task.id}")
                                    }
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }
        }

        if (isUpdating) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = GoldYellow)
            }
        }
    }
}

@Composable
fun ErrorMessageCard(
    message: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFE57373)
        )
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(16.dp),
            color = Color(0xFFC62828),
            fontWeight = FontWeight.SemiBold
        )
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
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Text(
            text = "Hari $day",
            fontSize = 12.sp,
            color = if (isSelected) {
                BlackSolid
            } else {
                Color.Gray
            }
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
                    width = 1.dp,
                    color = if (isSelected || isCompleted) {
                        Color.Transparent
                    } else {
                        Color.LightGray
                    },
                    shape = CircleShape
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
        colors = CardDefaults.cardColors(
            containerColor = GreenDeep
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
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

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = {
                        progress
                    },
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
    isUpdating: Boolean,
    onCheckedChange: () -> Unit,
    onItemClick: () -> Unit
) {
    val isChecked = task.isDone

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isChecked) {
                GoldYellow.copy(alpha = 0.15f)
            } else {
                Color(0xFFFFF9E6)
            }
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isChecked) {
                GoldYellow
            } else {
                Color.Black.copy(alpha = 0.1f)
            }
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
                    .background(
                        if (isChecked) {
                            GoldYellow
                        } else {
                            Color.White
                        }
                    )
                    .border(
                        width = 2.dp,
                        color = GoldYellow,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable(
                        enabled = !isUpdating
                    ) {
                        onCheckedChange()
                    },
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
                fontWeight = FontWeight.Bold,
                color = GreenDeep,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
