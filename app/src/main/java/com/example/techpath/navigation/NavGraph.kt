package com.example.techpath.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.techpath.ui.screens.Display
import com.example.techpath.ui.screens.Home
import com.example.techpath.viewModel.TechPathViewModel


@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: TechPathViewModel = viewModel()
    NavHost(navController = navController, startDestination = Routes.Home.routes) {
        composable(Routes.Home.routes) {
            Home(navController, viewModel)
        }
        composable(Routes.Display.routes) {
            Display(navController, viewModel)
        }
    }
}

