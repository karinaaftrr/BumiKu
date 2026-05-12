package com.example.bumiku.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bumiku.R
import com.example.bumiku.ui.theme.GreenDeep

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {

    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(scrollState),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = null,
            modifier = Modifier.size(130.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Daftar",
            style = MaterialTheme.typography.titleLarge,
            color = GreenDeep
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,

            onValueChange = {
                name = it
            },

            label = {
                Text(
                    text = "Nama Lengkap",
                    style = MaterialTheme.typography.bodyMedium
                )
            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(12.dp),

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = GreenDeep
                )
            },

            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,

            onValueChange = {
                email = it
            },

            label = {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodyMedium
                )
            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(12.dp),

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = GreenDeep
                )
            },

            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,

            onValueChange = {
                password = it
            },

            label = {
                Text(
                    text = "Kata Sandi",
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
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
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
                        imageVector =
                            if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,

                        contentDescription = null,
                        tint = GreenDeep
                    )
                }
            },

            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onRegisterSuccess,

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = GreenDeep
            ),

            shape = RoundedCornerShape(25.dp)
        ) {

            Text(
                text = "Daftar",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {

            Text(
                text = "Sudah punya akun? ",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Masuk",

                color = GreenDeep,

                style = MaterialTheme.typography.titleSmall,

                modifier = Modifier.clickable {
                    onLoginClick()
                }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}