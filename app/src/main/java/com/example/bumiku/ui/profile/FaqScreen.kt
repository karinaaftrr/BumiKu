package com.example.bumiku.ui.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun FaqScreen(onBackClick: () -> Unit) {
    var feedbackText by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) } // State untuk loading
    val context = LocalContext.current

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
                        contentDescription = "Kembali",
                        tint = GoldYellow,
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable { onBackClick() }
                    )
                    Text(
                        text = "Bantuan & FAQ",
                        style = MaterialTheme.typography.titleLarge,
                        color = GoldYellow,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                placeholder = { Text("Tulis pesanmu di sini...", style = MaterialTheme.typography.bodyMedium) },
                shape = RoundedCornerShape(16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                enabled = !isSending, // Disable input saat mengirim
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenDeep,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (feedbackText.isBlank()) {
                        Toast.makeText(context, "Pesan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    } else {
                        isSending = true
                        val db = FirebaseFirestore.getInstance()
                        val feedbackData = hashMapOf(
                            "message" to feedbackText,
                            "timestamp" to System.currentTimeMillis(),
                            "device" to android.os.Build.MODEL
                        )

                        db.collection("feedbacks")
                            .add(feedbackData)
                            .addOnSuccessListener {
                                isSending = false
                                feedbackText = "" // Reset form
                                Toast.makeText(context, "Feedback berhasil dikirim! Terima kasih.", Toast.LENGTH_LONG).show()
                            }
                            .addOnFailureListener { e ->
                                isSending = false
                                Toast.makeText(context, "Gagal mengirim: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSending, // Mencegah klik ganda saat loading
                colors = ButtonDefaults.buttonColors(containerColor = GoldYellow),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isSending) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp), // Pindahkan size ke dalam modifier
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    // ... sisa kode lainnya tetap sama
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
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