package com.example.bumiku.ui.wtrack

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.viewmodel.WTrackViewModel

@Composable
fun TrackerDetailScreen(
    navController: NavController,
    viewModel: WTrackViewModel,
    taskId: Int
) {
    val allTasks by viewModel.allTasks.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isUpdating by viewModel.isUpdating.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    val task = allTasks.find {
        it.id == taskId
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = GoldYellow)
        }
        return
    }

    if (task == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            ErrorMessageCard(
                message = errorMessage ?: "Tugas tidak ditemukan."
            )
        }
        return
    }

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
                        text = "WTracker Detail",
                        color = GoldYellow,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
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
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = GreenDeep
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = GoldYellow,
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 32.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color.White.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "⏱ ${task.estimasiWaktu}",
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    ),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = GoldYellow
                            ) {
                                Text(
                                    text = task.poin,
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    ),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = GreenDeep,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Langkah-langkah",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = GoldYellow,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                task.steps.forEachIndexed { index, step ->
                    StepCard(
                        stepNumber = index + 1,
                        stepText = step
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = GoldYellow.copy(alpha = 0.1f)
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = GoldYellow.copy(alpha = 0.3f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "💡",
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = task.tip,
                            style = MaterialTheme.typography.bodyMedium,
                            color = GreenDeep,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.completeTask(
                            taskId = task.id,
                            onSuccess = {
                                navController.popBackStack()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenDeep,
                        disabledContainerColor = GreenDeep.copy(alpha = 0.45f)
                    ),
                    enabled = !isUpdating && !task.isDone
                ) {
                    if (isUpdating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = GoldYellow,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = if (task.isDone) {
                                "Misi Selesai"
                            } else {
                                "Selesaikan Misi"
                            },
                            color = GoldYellow,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
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
}

@Composable
private fun StepCard(
    stepNumber: Int,
    stepText: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Black.copy(alpha = 0.05f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Langkah $stepNumber",
                style = MaterialTheme.typography.titleSmall,
                color = GreenDeep,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stepText,
                style = MaterialTheme.typography.bodyMedium,
                color = BlackSolid.copy(alpha = 0.8f)
            )
        }
    }
}
