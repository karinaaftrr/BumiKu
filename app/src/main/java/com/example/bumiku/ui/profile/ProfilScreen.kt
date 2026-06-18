package com.example.bumiku.ui.profile

import android.graphics.BitmapFactory
import android.util.Base64
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.bumiku.R
import com.example.bumiku.ui.theme.BlackSolid
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.ui.theme.RedWarning
import com.example.bumiku.viewmodel.AuthViewModel
import com.example.bumiku.viewmodel.WComViewModel
import com.example.bumiku.viewmodel.WTrackViewModel

@Composable
fun ProfilScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    komunitasViewModel: WComViewModel,
    wTrackViewModel: WTrackViewModel
) {
    val user by authViewModel.currentUser.collectAsStateWithLifecycle()
    val allTasks by wTrackViewModel.allTasks.collectAsStateWithLifecycle()
    val selectedDay by wTrackViewModel.selectedDay.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }

    val tasksToday = allTasks.filter { it.day == selectedDay }
    val totalMisi = tasksToday.size
    val misiSelesai = tasksToday.count { it.isDone }

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

        // Box foto profil — ditutup dengan benar setelah tombol edit
        Box(
            modifier = Modifier
                .size(130.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            // Tampilkan foto
            when {
                !user?.profileImageBase64.isNullOrBlank() -> {
                    val bytes = Base64.decode(user!!.profileImageBase64, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF0F0F0))
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )
                }

                !user?.profileImageUrl.isNullOrBlank() -> {
                    AsyncImage(
                        model = user?.profileImageUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF0F0F0))
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = R.drawable.login),
                        placeholder = painterResource(id = R.drawable.login)
                    )
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF0F0F0))
                            .align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(56.dp),
                            tint = Color.Gray
                        )
                    }
                }
            }

            // Tombol edit pensil di pojok kanan bawah
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(GoldYellow)
                    .align(Alignment.BottomEnd)
                    .clickable { navController.navigate("edit_akun") },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        } // ← Box foto profil ditutup di sini

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = user?.fullName ?: "Pengguna",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = BlackSolid
            )
            Text(
                text = user?.email ?: "Belum ada email",
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
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("tracker") },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
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

                    Column(modifier = Modifier.weight(1f)) {
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

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            ProfileSectionTitle(title = "Informasi & Panduan")

            ProfileMenuItem(
                icon = Icons.AutoMirrored.Filled.MenuBook,
                label = "Edukasi & Panduan",
                onClick = { navController.navigate("panduan") }
            )

            ProfileMenuItem(
                icon = Icons.AutoMirrored.Filled.HelpOutline,
                label = "Bantuan & FAQ",
                onClick = { navController.navigate("faq") }
            )

            ProfileMenuItem(
                icon = Icons.Default.Info,
                label = "Tentang BumiKu",
                onClick = { navController.navigate("tentang") }
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
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )

            ProfileMenuItem(
                icon = Icons.Default.Delete,
                label = "Hapus Akun",
                labelColor = RedWarning,
                showArrow = false,
                onClick = { showDeleteDialog = true }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Hapus Akun",
                    fontWeight = FontWeight.Bold,
                    color = RedWarning
                )
            },
            text = {
                Text("Akun kamu akan dihapus permanen dan tidak bisa dikembalikan. Yakin ingin melanjutkan?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        authViewModel.deleteAccount(
                            onSuccess = {
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }
                ) {
                    Text("Hapus", color = RedWarning, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
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