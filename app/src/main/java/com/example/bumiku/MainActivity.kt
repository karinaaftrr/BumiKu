package com.example.bumiku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bumiku.ui.navigation.Screen
import com.example.bumiku.ui.navigation.SetupNavGraph
import com.example.bumiku.ui.theme.BumiKuTheme
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.theme.GreenDeep
import com.example.bumiku.viewmodel.AuthViewModel
import com.example.bumiku.viewmodel.WComViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BumiKuTheme {
                val navController = rememberNavController()

                val komunitasViewModel: WComViewModel = viewModel()
                val authViewModel: AuthViewModel = viewModel()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomBar =
                    currentRoute == Screen.Dashboard.route ||
                            currentRoute == Screen.Notifications.route ||
                            currentRoute == Screen.Profile.route

                val isFullScreenRoute =
                    currentRoute == Screen.Onboarding.route ||
                            currentRoute == Screen.Login.route ||
                            currentRoute == Screen.Register.route ||
                            currentRoute == Screen.ForgotPassword.route ||
                            currentRoute == null

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            GlobalBottomNavigationBar(
                                navController = navController,
                                currentRoute = currentRoute
                            )
                        }
                    }
                ) { innerPadding ->
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
fun GlobalBottomNavigationBar(
    navController: NavHostController,
    currentRoute: String?
) {
    val items = listOf(
        Screen.Dashboard,
        Screen.Notifications,
        Screen.Profile
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.Dashboard.route) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    screen.icon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = screen.label,
                            modifier = Modifier.size(24.dp),
                            tint = if (currentRoute == screen.route) {
                                GreenDeep
                            } else {
                                Color.Gray
                            }
                        )
                    }
                },
                label = {
                    Text(
                        text = screen.label,
                        color = if (currentRoute == screen.route) {
                            GreenDeep
                        } else {
                            Color.Gray
                        },
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
