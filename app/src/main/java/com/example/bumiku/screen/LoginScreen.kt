package com.example.bumiku.screen

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bumiku.R
import com.example.bumiku.ui.theme.GreenDeep

@Composable
fun LoginScreen(
    onLoginSuccess: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null //
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Login Illustration",
            modifier = Modifier.size(220.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = GreenDeep
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = GreenDeep) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenDeep,
                focusedLabelColor = GreenDeep,
                cursorColor = GreenDeep
            ),
            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = GreenDeep) },
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description, tint = GreenDeep)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenDeep,
                focusedLabelColor = GreenDeep,
                cursorColor = GreenDeep
            ),
            singleLine = true,
            enabled = !isLoading
        )

        Text(
            text = "Forgot Password?",
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
                .clickable { /* Handle forgot password */ },
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        if (isLoading) {
            CircularProgressIndicator(color = GreenDeep)
        } else {
            Button(
                onClick = {
                    if (email.trim().isNotEmpty() && password.trim().isNotEmpty()) {
                        onLoginSuccess(email.trim(), password.trim())
                    } else {
                        Toast.makeText(context, "Harap isi email dan password", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenDeep),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text("Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "or", color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = { /* Google Login */ },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(27.dp),
            enabled = !isLoading
        ) {
            Text("Continue with Google", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Belum punya akun? ", color = Color.Gray)
            Text(
                text = "Buat Akun",
                color = GreenDeep,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { if (!isLoading) onRegisterClick() }
            )
        }
    }
}