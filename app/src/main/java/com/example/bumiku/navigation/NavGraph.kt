package com.example.bumiku.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.bumiku.model.WasteSource
import com.example.bumiku.screen.*
import com.example.bumiku.viewmodel.KomunitasViewModel

sealed class Screen(val route: String, val label: String? = null, val icon: ImageVector? = null) {
    object Onboarding : Screen("onboarding")
    object Dashboard : Screen("dashboard", "Beranda", Icons.Default.Home)
    object Challenge : Screen("challenge", "Challenge", Icons.AutoMirrored.Filled.Assignment)
    object Notifications : Screen("notification", "Notifikasi", Icons.Default.Notifications)
    object WasteCommunity : Screen("waste_community", "WComm")
    object History : Screen("history_wcom")
    object WasteGuide : Screen("waste_guide")
    object Tracker : Screen("tracker")
    object News : Screen("wnews")
    object Settings : Screen("settings", "Pengaturan", Icons.Default.Settings)
    object Profile : Screen("profil", "Profil", Icons.Default.Person)
    object Panduan : Screen("panduan")
    object FAQ : Screen("faq")
    object Tentang : Screen("tentang")
}

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: KomunitasViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route,
        modifier = modifier
    ) {
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(onGetStartedClick = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            })
        }
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                navController = navController,
                viewModel = viewModel,
                onNotificationClick = {
                    navController.navigate(Screen.Notifications.route)
                }
            )
        }
        composable(route = Screen.Notifications.route) {
            NotificationScreen(onBackClick = {
                navController.popBackStack()
            })
        }
        composable(route = Screen.WasteCommunity.route) {
            WasteCommunity(navController = navController, viewModel = viewModel)
        }
        composable(route = "detail/{judul}") { backStackEntry ->
            val judul = backStackEntry.arguments?.getString("judul")
            val komunitas = viewModel.listKomunitas.find { it.judul == judul }

            komunitas?.let { data ->
                DetailWCom(
                    komunitas = data,
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
        composable(route = Screen.History.route) {
            HistoryWCom(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.WasteGuide.route) {
            WasteGuideScreen(navController = navController)
        }
        composable(route = "guide_detail/{categoryId}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            val category = WasteSource.categories.find { it.id == categoryId }
            category?.let {
                DetailWasteGuideScreen(category = it, navController = navController)
            }
        }
        composable(route = Screen.Tracker.route) {
            TrackerScreen(navController = navController)
        }
        composable(
            route = "tracker_detail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            TrackerDetailScreen(navController = navController, taskId = taskId)
        }
        composable(route = Screen.News.route) {
            WNewsScreen(navController = navController)
        }
        composable(route = Screen.Profile.route) {
            ProfilScreen(navController = navController)
        }
        composable(route = Screen.Panduan.route) {
            PanduanScreen(onBackClick = { navController.popBackStack() })
        }
        composable(route = Screen.FAQ.route) {
            FaqScreen(onBackClick = { navController.popBackStack() })
        }
        composable(route = Screen.Tentang.route) {
            TentangScreen(onBackClick = { navController.popBackStack() })
        }
        // Placeholder for other screens
        composable(route = Screen.Challenge.route) { /* TODO */ }
        composable(route = Screen.Settings.route) { /* TODO */ }
    }
}
