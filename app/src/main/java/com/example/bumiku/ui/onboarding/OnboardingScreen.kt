package com.example.bumiku.ui.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bumiku.R
import com.example.bumiku.ui.theme.GreenDeep
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            userScrollEnabled = true
        ) { page ->
            when (page) {
                0 -> OnboardingOneContent(
                    onSkip = onFinish
                )

                1 -> OnboardingTwoContent(
                    onSkip = onFinish
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomIndicator(
                activePage = pagerState.currentPage,
                pageCount = 2
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (pagerState.currentPage == 1) {
                        onFinish()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenDeep
                ),
                shape = RoundedCornerShape(27.dp)
            ) {
                Text(
                    text = if (pagerState.currentPage == 1) "Mulai" else "Next",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun OnboardingOneContent(
    onSkip: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.sampah),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Skip",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clickable {
                    onSkip()
                },
            color = Color.White
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(
                topStart = 40.dp,
                topEnd = 40.dp
            ),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = "Masa depan pengelolaan\nlimbah yang cerdas",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = GreenDeep
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "BumiKu membantu Anda mengontrol pengelolaan sampah dan menjaga kelestarian bumi kita.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun OnboardingTwoContent(
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Skip",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clickable {
                        onSkip()
                    },
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.weight(0.15f))

        Image(
            painter = painterResource(id = R.drawable.bumi),
            contentDescription = null,
            modifier = Modifier.size(240.dp)
        )

        Text(
            text = "Personalisasi",
            style = MaterialTheme.typography.titleLarge,
            color = GreenDeep
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "BumiKu membantu Anda mengendalikan pengelolaan sampah dan melestarikan bumi kita.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.weight(0.3f))
    }
}

@Composable
fun CustomIndicator(
    activePage: Int,
    pageCount: Int
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { iteration ->
            val isSelected = activePage == iteration

            val width by animateDpAsState(
                targetValue =
                    if (isSelected)
                        28.dp
                    else
                        8.dp,
                label = ""
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected)
                            GreenDeep
                        else
                            Color(0xFFE0E0E0)
                    )
                    .width(width)
                    .height(8.dp)
            )
        }
    }
}