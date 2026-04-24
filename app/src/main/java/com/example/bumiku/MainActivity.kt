package com.example.bumiku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bumiku.ui.theme.BumiKuTheme
import com.example.bumiku.screen.WasteCommunity
import com.example.bumiku.screen.DetailWCom
import com.example.bumiku.screen.HistoryWCom
import com.example.bumiku.viewmodel.KomunitasViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BumiKuTheme {
                val navController = rememberNavController()
                val viewModel: KomunitasViewModel = viewModel()

                Scaffold { innerPadding ->
                    AppNavigation(
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
fun AppNavigation(
    navController: NavHostController,
    viewModel: KomunitasViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "waste_community",
        modifier = modifier
    ) {
        composable("waste_community") {
            WasteCommunity(navController = navController, viewModel = viewModel)
        }
        composable("detail/{judul}") { backStackEntry ->
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
        composable("history_wcom") {
            HistoryWCom(navController = navController, viewModel = viewModel)
        }
    }
}