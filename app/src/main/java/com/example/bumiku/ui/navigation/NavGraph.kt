package com.example.bumiku.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bumiku.data.model.WasteCategory
import com.example.bumiku.data.repository.GuideRepository
import com.example.bumiku.ui.auth.ForgotPasswordScreen
import com.example.bumiku.ui.auth.LoginScreen
import com.example.bumiku.ui.auth.RegisterScreen
import com.example.bumiku.ui.dashboard.DashboardScreen
import com.example.bumiku.ui.notification.NotificationScreen
import com.example.bumiku.ui.onboarding.OnboardingScreen
import com.example.bumiku.ui.profile.EditAkunScreen
import com.example.bumiku.ui.profile.FaqScreen
import com.example.bumiku.ui.profile.ProfilScreen
import com.example.bumiku.ui.profile.TentangScreen
import com.example.bumiku.ui.theme.GoldYellow
import com.example.bumiku.ui.wcom.DetailWCom
import com.example.bumiku.ui.wcom.HistoryWCom
import com.example.bumiku.ui.wcom.WComScreen
import com.example.bumiku.ui.wguide.DetailWasteGuideScreen
import com.example.bumiku.ui.wguide.PanduanScreen
import com.example.bumiku.ui.wguide.WGuideScreen
import com.example.bumiku.ui.wnews.DetailWNewsScreen
import com.example.bumiku.ui.wnews.WNewsScreen
import com.example.bumiku.ui.wtrack.TrackerDetailScreen
import com.example.bumiku.ui.wtrack.WTrackScreen
import com.example.bumiku.viewmodel.AuthViewModel
import com.example.bumiku.viewmodel.WComViewModel
import com.example.bumiku.viewmodel.WTrackViewModel
import com.google.firebase.auth.FirebaseAuth

sealed class Screen(
    val route: String,
    val label: String = "",
    val icon: ImageVector? = null
) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
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
    komunitasViewModel: WComViewModel,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val wTrackViewModel: WTrackViewModel = viewModel()

    val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
        Screen.Dashboard.route
    } else {
        Screen.Onboarding.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                onForgotPasswordClick = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                authViewModel = authViewModel
            )
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                },
                authViewModel = authViewModel
            )
        }

        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                navController = navController,
                komunitasViewModel = komunitasViewModel,
                wTrackViewModel = wTrackViewModel,
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

        composable(
            route = "detail_wcom/{communityId}",
            arguments = listOf(
                navArgument("communityId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val communityId = backStackEntry.arguments?.getInt("communityId") ?: 0
            val komunitas = komunitasViewModel.getCommunityById(communityId)

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
            WGuideScreen(
                navController = navController
            )
        }

        composable(route = "guide_detail/{categoryId}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")

            var category by remember {
                mutableStateOf<WasteCategory?>(null)
            }

            var isLoading by remember {
                mutableStateOf(true)
            }

            LaunchedEffect(categoryId) {
                val repository = GuideRepository()
                category = repository.getGuideById(categoryId)
                isLoading = false
            }

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = GoldYellow)
                    }
                }

                category != null -> {
                    DetailWasteGuideScreen(
                        category = category!!,
                        navController = navController
                    )
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Data panduan tidak ditemukan")
                    }
                }
            }
        }

        composable(route = Screen.Tracker.route) {
            WTrackScreen(
                navController = navController,
                viewModel = wTrackViewModel
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
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0

            TrackerDetailScreen(
                navController = navController,
                viewModel = wTrackViewModel,
                taskId = taskId
            )
        }

        composable(route = Screen.News.route) {
            WNewsScreen(
                navController = navController
            )
        }

        composable(
            route = "detail_news/{newsId}",
            arguments = listOf(
                navArgument("newsId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val newsId = backStackEntry.arguments?.getInt("newsId") ?: 0

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
