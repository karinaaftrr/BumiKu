package com.example.bumiku.screen

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bumiku.R
import com.example.bumiku.ui.theme.GreenDeep
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onLoginSuccess: (String, String) -> Unit,
    onRegisterSuccess: () -> Unit,
    isLoading: Boolean = false
) {
    val pagerState = rememberPagerState(pageCount = { 4 })
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
                    onSkip = {
                        scope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    }
                )

                1 -> OnboardingTwoContent(
                    onSkip = {
                        scope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    }
                )

                2 -> LoginOnboardingContent(
                    onLoginClick = onLoginSuccess,
                    onRegisterClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(3)
                        }
                    },
                    isLoading = isLoading
                )

                3 -> RegisterScreen(
                    onRegisterSuccess = onRegisterSuccess,
                    onLoginClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    }
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
                pageCount = 4
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 24.dp),

                contentAlignment = Alignment.Center
            ) {

                if (pagerState.currentPage < 2) {

                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1
                                )
                            }
                        },

                        modifier = Modifier.fillMaxSize(),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenDeep
                        ),

                        shape = RoundedCornerShape(27.dp)
                    ) {

                        Text(
                            text = "Next",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginOnboardingContent(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    isLoading: Boolean
) {

    val scrollState = rememberScrollState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = null,
            modifier = Modifier.size(160.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Masuk",
            style = MaterialTheme.typography.titleLarge,
            color = GreenDeep
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,

            onValueChange = {
                email = it
            },

            label = {
                Text(
                    "Email",
                    style = MaterialTheme.typography.bodyMedium
                )
            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(12.dp),

            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    null,
                    tint = GreenDeep
                )
            },

            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,

            onValueChange = {
                password = it
            },

            label = {
                Text(
                    "Kata Sandi",
                    style = MaterialTheme.typography.bodyMedium
                )
            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(12.dp),

            visualTransformation =
                if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    null,
                    tint = GreenDeep
                )
            },

            trailingIcon = {

                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {

                    Icon(
                        if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,

                        null,
                        tint = GreenDeep
                    )
                }
            },

            singleLine = true,
            enabled = !isLoading
        )

        Text(
            text = "Lupa kata sandi?",

            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
                .clickable { },

            color = Color.Gray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (isLoading) {

            CircularProgressIndicator(
                color = GreenDeep
            )

        } else {

            Button(
                onClick = {

                    if (email.isNotEmpty() && password.isNotEmpty()) {

                        onLoginClick(email, password)

                    } else {

                        Toast.makeText(
                            context,
                            "Harap isi email dan password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenDeep
                ),

                shape = RoundedCornerShape(25.dp)
            ) {

                Text(
                    text = "Masuk",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "atau",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {

            Text(
                "Belum punya akun? ",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Buat Akun",
                color = GreenDeep,
                style = MaterialTheme.typography.titleSmall,

                modifier = Modifier.clickable {

                    if (!isLoading) {
                        onRegisterClick()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun OnboardingOneContent(onSkip: () -> Unit) {

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
fun OnboardingTwoContent(onSkip: () -> Unit) {

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