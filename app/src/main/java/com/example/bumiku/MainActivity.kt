package com.example.bumiku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bumiku.ui.theme.BumiKuTheme
import com.example.bumiku.navigation.SetupNavGraph
import com.example.bumiku.viewmodel.KomunitasViewModel
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BumiKuTheme {
                val navController = rememberNavController()
                val viewModel: KomunitasViewModel = viewModel()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        if (currentRoute == "dashboard" || currentRoute == "notification" || currentRoute == "profil") {
                            GlobalBottomNavigationBar(navController, currentRoute)
                        }
                    }
                ) { innerPadding ->
                    SetupNavGraph(
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GlobalBottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    NavigationBar(containerColor = Color.White) {
        val items = listOf(
            NavigationItem("Beranda", "dashboard", Icons.Default.Home),
            NavigationItem("Notifikasi", "notification", Icons.Default.Notifications),
            NavigationItem("Profil", "profil", Icons.Default.Person)
        )
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo("dashboard") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { 
                    Icon(
                        imageVector = item.icon, 
                        contentDescription = item.label, 
                        modifier = Modifier.size(24.dp),
                        tint = if (currentRoute == item.route) GreenDeep else Color.Gray
                    ) 
                },
                label = { 
                    Text(
                        item.label, 
                        color = if (currentRoute == item.route) GreenDeep else Color.Gray,
                        style = MaterialTheme.typography.labelSmall
                    ) 
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = GoldYellow.copy(alpha = 0.2f)
                )
            )
        }
    }
}

data class NavigationItem(val label: String, val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
