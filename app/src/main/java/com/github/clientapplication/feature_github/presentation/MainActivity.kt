package com.github.clientapplication.feature_github.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.github.clientapplication.feature_github.presentation.MainActivity.Args.REPO_ID
import com.github.clientapplication.feature_github.presentation.navigation.NavRoutes
import com.github.clientapplication.feature_github.presentation.screen.DetailsScreen
import com.github.clientapplication.feature_github.presentation.screen.MainScreen
import com.github.clientapplication.feature_github.presentation.screen.SplashScreen
import com.github.clientapplication.ui.theme.GitHubClientApplicationTheme
import com.github.clientapplication.githubrepos.utils.Constants.TAG
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    object Args {
        const val REPO_ID = "repo_id"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubClientApplicationTheme {
                Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                    Navigation(viewModel)
                }
            }
        }
    }
}

@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route
    ) {
        composable(NavRoutes.Splash.route) {
            Log.d(TAG, NavRoutes.Splash.route)
            SplashScreen(navController = navController)
        }


        composable(NavRoutes.Main.route) {
            Log.d(TAG, NavRoutes.Main.route)
            MainScreen(
                state = viewModel.state,
                effectFlow = viewModel.effects.receiveAsFlow(),
                navController = navController
            )
        }

        composable(NavRoutes.RepoDetails.route) {
            Log.d(TAG, NavRoutes.RepoDetails.route)
            DetailsScreen(
                id = "MDEwOlJlcG9zaXRvcnk0MDgyMTcyODk=",
                navController = navController
            )
        }

        /*
        composable(
            route = NavRoutes.RepoDetails.route,
            arguments = listOf(navArgument(name = REPO_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            DetailsScreen(
                id = backStackEntry.arguments?.getString(REPO_ID),
                navController = navController
            )
        }*/

    }
}