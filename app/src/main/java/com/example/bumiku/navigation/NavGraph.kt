package com.example.bumiku.navigation

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bumiku.dashboard.DashboardScreen
import com.example.bumiku.model.WasteSource
import com.example.bumiku.screen.*
import com.example.bumiku.viewmodel.AuthViewModel
import com.example.bumiku.viewmodel.KomunitasViewModel
import com.example.bumiku.wcom.DetailWCom
import com.example.bumiku.wcom.HistoryWCom
import com.example.bumiku.wcom.WComScreen
import com.example.bumiku.wguide.DetailWasteGuideScreen
import com.example.bumiku.wguide.PanduanScreen
import com.example.bumiku.wguide.WasteGuideScreen
import com.example.bumiku.wnews.DetailWNewsScreen
import com.example.bumiku.wnews.WNewsScreen
import com.example.bumiku.wtrack.TrackerDetailScreen
import com.example.bumiku.wtrack.TrackerScreen

sealed class Screen(
    val route: String,
    val label: String = "",
    val icon: ImageVector? = null
) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard", "Beranda", Icons.Default.Home)
    object Notifications : Screen("notification", "Notifikasi", Icons.Default.Notifications)
    object WasteCommunity : Screen("waste_community", "WComm")
    object History : Screen("history_wcom")
    object WasteGuide : Screen("waste_guide")
    object Tracker : Screen("tracker")
    object News : Screen("wnews")
    object Profile : Screen("profil", "Profil", Icons.Default.Person)
    object EditAccount : Screen("edit_akun")
    object Panduan : Screen("panduan")
    object FAQ : Screen("faq")
    object Tentang : Screen("tentang")
}

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    komunitasViewModel: KomunitasViewModel,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route,
        modifier = modifier
    ) {

        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                onLoginSuccess = { email, password ->
                    if (authViewModel.login(email, password)) {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Onboarding.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            authViewModel.errorMessage ?: "Login Gagal",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },

                onRegisterSuccess = {
                    navController.navigate(Screen.Register.route)
                },

                isLoading = authViewModel.isLoading
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { email, password ->
                    if (authViewModel.login(email, password)) {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            authViewModel.errorMessage ?: "Login Gagal",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },

                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },

                isLoading = authViewModel.isLoading
            )
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                },

                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                navController = navController,
                viewModel = komunitasViewModel,
                authViewModel = authViewModel,

                onNotificationClick = {
                    navController.navigate(Screen.Notifications.route)
                }
            )
        }

        composable(route = Screen.Notifications.route) {
            NotificationScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.WasteCommunity.route) {
            WComScreen(
                navController = navController,
                viewModel = komunitasViewModel
            )
        }

        composable(route = "detail/{judul}") { backStackEntry ->

            val judul = backStackEntry.arguments?.getString("judul")

            val komunitas = komunitasViewModel.listKomunitas.find {
                it.judul == judul
            }

            komunitas?.let { data ->
                DetailWCom(
                    komunitas = data,
                    navController = navController,
                    viewModel = komunitasViewModel
                )
            }
        }

        composable(route = Screen.History.route) {
            HistoryWCom(
                navController = navController,
                viewModel = komunitasViewModel
            )
        }

        composable(route = Screen.WasteGuide.route) {
            WasteGuideScreen(navController = navController)
        }

        composable(route = "guide_detail/{categoryId}") { backStackEntry ->

            val categoryId =
                backStackEntry.arguments?.getString("categoryId")

            val category = WasteSource.categories.find {
                it.id == categoryId
            }

            category?.let {
                DetailWasteGuideScreen(
                    category = it,
                    navController = navController
                )
            }
        }

        composable(route = Screen.Tracker.route) {
            TrackerScreen(
                navController = navController,
                viewModel = komunitasViewModel
            )
        }

        composable(
            route = "tracker_detail/{taskId}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val taskId =
                backStackEntry.arguments?.getInt("taskId") ?: 0

            TrackerDetailScreen(
                navController = navController,
                viewModel = komunitasViewModel,
                taskId = taskId
            )
        }

        composable(route = Screen.News.route) {
            WNewsScreen(navController = navController)
        }

        composable(
            route = "detail_news/{newsId}",
            arguments = listOf(
                navArgument("newsId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val newsId =
                backStackEntry.arguments?.getInt("newsId") ?: 0

            DetailWNewsScreen(
                navController = navController,
                newsId = newsId
            )
        }

        composable(route = Screen.Profile.route) {
            ProfilScreen(
                navController = navController,
                authViewModel = authViewModel,
                komunitasViewModel = komunitasViewModel
            )
        }

        composable(route = Screen.EditAccount.route) {
            EditAkunScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(route = Screen.Panduan.route) {
            PanduanScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.FAQ.route) {
            FaqScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Tentang.route) {
            TentangScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}