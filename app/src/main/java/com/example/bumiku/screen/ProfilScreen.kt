package com.example.bumiku.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bumiku.R
import com.example.bumiku.navigation.Screen
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.ui.theme.RedWarning
import com.example.bumiku.viewmodel.AuthViewModel
import com.example.bumiku.viewmodel.KomunitasViewModel
import model.User

@Composable
fun ProfilScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    komunitasViewModel: KomunitasViewModel
) {
    val user: User? = authViewModel.currentUser

    val profileImage = when (user?.email) {
        "tiwi@gmail.com" -> R.drawable.tiwi
        "karina@gmail.com" -> R.drawable.karina
        "rama@gmail.com" -> R.drawable.rama
        "rani@gmail.com" -> R.drawable.rani
        else -> R.drawable.login
    }

    val totalMisi = komunitasViewModel.allTasks.size
    val misiSelesai = komunitasViewModel.allTasks.count { it.isDone }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Surface(
            color = GreenDeep,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Profil",
                    style = MaterialTheme.typography.titleLarge,
                    color = GoldYellow,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .size(130.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = profileImage),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF0F0F0))
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(GoldYellow)
                    .align(Alignment.BottomEnd)
                    .clickable { 
                        navController.navigate(Screen.EditAccount.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = user?.fullName ?: "Guest",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = BlackSolid
            )
            Text(
                text = user?.email ?: "guest@bumiku.com",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(GreenDeep.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = GreenDeep,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "$misiSelesai/$totalMisi",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 18.sp,
                            color = BlackSolid
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Misi Selesai Hari Ini",
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            ProfileSectionTitle(title = "Informasi & Panduan")
            ProfileMenuItem(
                icon = Icons.AutoMirrored.Filled.MenuBook,
                label = "Edukasi & Panduan",
                onClick = { navController.navigate(Screen.Panduan.route) }
            )
            ProfileMenuItem(
                icon = Icons.AutoMirrored.Filled.HelpOutline,
                label = "Bantuan & FAQ",
                onClick = { navController.navigate(Screen.FAQ.route) }
            )
            ProfileMenuItem(
                icon = Icons.Default.Info,
                label = "Tentang BumiKu",
                onClick = { navController.navigate(Screen.Tentang.route) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileSectionTitle(title = "Akun")
            ProfileMenuItem(
                icon = Icons.AutoMirrored.Filled.Logout,
                label = "Keluar",
                labelColor = RedWarning,
                showArrow = false,
                onClick = {
                    authViewModel.logout()
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
            ProfileMenuItem(
                icon = Icons.Default.Delete,
                label = "Hapus Akun",
                labelColor = RedWarning,
                showArrow = false
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun ProfileSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = Color.Gray,
        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    label: String,
    labelColor: Color = BlackSolid,
    showArrow: Boolean = true,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(GoldYellow.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = GreenDeep,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = labelColor,
                modifier = Modifier.weight(1f)
            )
            if (showArrow) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }
        }
    }
}
