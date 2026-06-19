package com.example.bumiku.ui.wnews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bumiku.data.model.News
import com.example.bumiku.data.repository.NewsRepository
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep

@Composable
fun DetailWNewsScreen(
    navController: NavController,
    newsId: Int
) {
    var news by remember { mutableStateOf<News?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(newsId) {
        isLoading = true
        errorMessage = null

        val data = NewsRepository().getNews()
        val selectedNews = data.find {
            it.id == newsId
        }

        if (selectedNews == null) {
            errorMessage = "Berita tidak ditemukan. Cek koneksi internet kamu."
        } else {
            news = selectedNews
        }

        isLoading = false
    }

    when {
        isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = GoldYellow)
            }
        }

        errorMessage != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                ErrorNewsCard(
                    message = errorMessage ?: "Data gagal dimuat."
                )
            }
        }

        news != null -> {
            DetailNewsContent(
                navController = navController,
                news = news!!
            )
        }
    }
}

@Composable
private fun DetailNewsContent(
    navController: NavController,
    news: News
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
                    contentDescription = "Back",
                    tint = GoldYellow,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            navController.popBackStack()
                        }
                )

                Text(
                    text = "Detail Berita",
                    style = MaterialTheme.typography.titleLarge,
                    color = GoldYellow,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(
                    id = getNewsImage(news.imageName)
                ),
                contentDescription = news.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = news.date,
                    style = MaterialTheme.typography.labelMedium,
                    color = GreenDeep.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = BlackSolid
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Text(
                        text = news.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = BlackSolid.copy(alpha = 0.8f),
                        modifier = Modifier.padding(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}