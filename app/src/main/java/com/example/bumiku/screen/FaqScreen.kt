package com.example.bumiku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.ui.theme.BlackSolid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(onBackClick: () -> Unit) {
    var feedbackText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Bantuan & FAQ", 
                        color = GoldYellow, 
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = GoldYellow)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreenDeep)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text(
                text = "Pertanyaan Umum",
                style = MaterialTheme.typography.titleMedium,
                color = GreenDeep
            )
            Spacer(modifier = Modifier.height(16.dp))

            FaqItem(
                question = "Apa itu BumiKu?",
                answer = "BumiKu adalah aplikasi asisten lingkungan yang membantumu membangun kebiasaan ramah lingkungan melalui pelacakan aktivitas dan edukasi."
            )
            FaqItem(
                question = "Bagaimana cara mendapatkan poin?",
                answer = "Kamu bisa mendapatkan poin dengan menyelesaikan tugas harian di menu WTrack dan berpartisipasi dalam event komunitas."
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Kirim Feedback",
                style = MaterialTheme.typography.titleMedium,
                color = GreenDeep
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Punya saran atau menemukan masalah? Beritahu kami!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                placeholder = { Text("Tulis pesanmu di sini...", style = MaterialTheme.typography.bodyMedium) },
                shape = RoundedCornerShape(16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenDeep,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Handle send feedback */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = GoldYellow),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Kirim Pesan", 
                    color = Color.White, 
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun FaqItem(question: String, answer: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = question, 
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), 
            color = BlackSolid
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = answer, 
            style = MaterialTheme.typography.bodyMedium, 
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
    }
}
