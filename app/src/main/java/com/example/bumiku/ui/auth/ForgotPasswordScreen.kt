package com.example.bumiku.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bumiku.ui.theme.*
import com.example.bumiku.viewmodel.AuthViewModel

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val isLoading by authViewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by authViewModel.errorMessage.collectAsStateWithLifecycle()

    val context = androidx.compose.ui.platform.LocalContext.current

    var email by remember { mutableStateOf("") }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            authViewModel.clearError()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4FAF7))
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Kembali",
                        tint = BlackSolid
                    )
                }

                Text(
                    text = "BumiKu",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = GreenDeep,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Lupa Kata Sandi?",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = BlackSolid
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Masukkan email akun kamu. Kami akan mengirim link untuk mengatur ulang kata sandi.",
                fontSize = 15.sp,
                color = Color.Gray,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Email",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = BlackSolid
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("nama@email.com") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null,
                        tint = GreenDeep
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor = GreenDeep,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = GreenDeep
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    when {
                        email.isBlank() -> {
                            Toast.makeText(
                                context,
                                "Email wajib diisi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        !android.util.Patterns.EMAIL_ADDRESS
                            .matcher(email.trim())
                            .matches() -> {
                            Toast.makeText(
                                context,
                                "Format email tidak valid",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            authViewModel.resetPassword(
                                email = email.trim(),
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Link reset password dikirim ke email",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onBackClick()
                                }
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenDeep,
                    disabledContainerColor = GreenDeep.copy(alpha = 0.7f)
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = GoldYellow,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Kirim Link Reset",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}