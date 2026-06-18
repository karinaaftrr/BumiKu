package com.example.bumiku.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bumiku.ui.theme.*
import com.example.bumiku.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val isLoading by authViewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by authViewModel.errorMessage.collectAsStateWithLifecycle()

    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            authViewModel.clearError()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color(0xFFF4FAF7),
                        Color(0xFFEAF5EE)
                    )
                )
            )
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onLoginClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = GreenDeep
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "BumiKu",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = GreenDeep
                )

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Mulai Jaga Bumi",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = GreenDeep
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Buat akun untuk mulai membangun kebiasaan ramah lingkungan.",
                fontSize = 15.sp,
                color = Color.Gray,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Nama",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = BlackSolid
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Minimal 10 karakter") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = GreenDeep
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                singleLine = true,
                colors = authTextFieldColorsRegister()
            )

            Spacer(modifier = Modifier.height(18.dp))

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
                placeholder = { Text("example@gmail.com") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = GreenDeep
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = authTextFieldColorsRegister()
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Kata Sandi",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = BlackSolid
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Minimal 6 karakter") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = GreenDeep
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { showPassword = !showPassword }
                    ) {
                        Icon(
                            imageVector =
                                if (showPassword)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = GreenDeep
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                singleLine = true,
                visualTransformation =
                    if (showPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = authTextFieldColorsRegister()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    when {
                        username.isBlank() || email.isBlank() || password.isBlank() -> {
                            Toast.makeText(
                                context,
                                "Semua data wajib diisi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        username.trim().length < 10 -> {
                            Toast.makeText(
                                context,
                                "fullname minimal 10 karakter",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                            Toast.makeText(
                                context,
                                "Format email tidak valid",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        password.trim().length < 6 -> {
                            Toast.makeText(
                                context,
                                "Password minimal 6 karakter",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            authViewModel.register(
                                fullname = username.trim(),
                                email = email.trim(),
                                password = password.trim(),
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Registrasi berhasil, silakan login",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onRegisterSuccess()
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
                        text = "Daftar Sekarang",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sudah punya akun? ",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Text(
                    text = "Masuk",
                    fontSize = 14.sp,
                    color = GreenDeep,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        if (!isLoading) {
                            onLoginClick()
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun authTextFieldColorsRegister() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black,
    disabledTextColor = Color.Black,
    focusedBorderColor = GreenDeep,
    unfocusedBorderColor = Color(0xFFE0E0E0),
    disabledBorderColor = Color(0xFFE0E0E0),
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    disabledContainerColor = Color.White,
    focusedLabelColor = GreenDeep,
    unfocusedLabelColor = BlackSolid,
    cursorColor = GreenDeep
)
