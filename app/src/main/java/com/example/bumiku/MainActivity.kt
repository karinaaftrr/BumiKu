package com.example.bumiku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.bumiku.navigation.Screen
import com.example.bumiku.viewmodel.KomunitasViewModel
import com.example.bumiku.viewmodel.AuthViewModel
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BumiKuTheme {
                val navController = rememberNavController()
                val komunitasViewModel: KomunitasViewModel = viewModel()
                val authViewModel: AuthViewModel = viewModel()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        if (currentRoute == Screen.Dashboard.route ||
                            currentRoute == Screen.Notifications.route ||
                            currentRoute == Screen.Profile.route) {
                            GlobalBottomNavigationBar(navController, currentRoute)
                        }
                    }
                ) { innerPadding ->
                    val isFullScreenRoute = currentRoute == Screen.Onboarding.route ||
                            currentRoute == Screen.Login.route ||
                            currentRoute == Screen.Register.route ||
                            currentRoute == null

                    SetupNavGraph(
                        navController = navController,
                        komunitasViewModel = komunitasViewModel,
                        authViewModel = authViewModel,
                        modifier = if (isFullScreenRoute) {
                            Modifier
                        } else {
                            Modifier.padding(innerPadding)
                        }
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
            Screen.Dashboard,
            Screen.Notifications,
            Screen.Profile
        )
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.Dashboard.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    screen.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = screen.label,
                            modifier = Modifier.size(24.dp),
                            tint = if (currentRoute == screen.route) GreenDeep else Color.Gray
                        )
                    }
                },
                label = {
                    Text(
                        screen.label,
                        color = if (currentRoute == screen.route) GreenDeep else Color.Gray,
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