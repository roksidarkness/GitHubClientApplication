package com.github.clientapplication.feature_github.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.clientapplication.feature_github.presentation.MainViewModel
import com.github.clientapplication.feature_github.presentation.screen.DetailsScreen
import com.github.clientapplication.feature_github.presentation.screen.MainScreen
import com.github.clientapplication.feature_github.presentation.screen.SplashScreen
import com.github.clientapplication.githubrepos.utils.Constant
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun Navigation(viewModel: MainViewModel, height: Int, weight: Int) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route,

        ) {
        composable(NavRoutes.Splash.route) {
            SplashScreen(navController = navController, height, weight)
        }
        composable(NavRoutes.Main.route) {
            MainScreen(
                viewModel = viewModel,
                effectFlow = viewModel.effects.receiveAsFlow(),
                navController = navController
            )
        }
        composable(
            route = NavRoutes.RepoDetails.route,
            arguments = listOf(navArgument(Constant.REPO_DETAILS_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(Constant.REPO_DETAILS_ARGUMENT_KEY)
                ?.let {
                    DetailsScreen(
                        it,
                        viewModel = viewModel,
                        effectFlow = viewModel.effects.receiveAsFlow()
                    )
                }
        }
    }
}