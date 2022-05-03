package com.github.clientapplication.feature_github.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.clientapplication.feature_github.presentation.navigation.NavRoutes
import com.github.clientapplication.feature_github.presentation.screen.MainScreen
import com.github.clientapplication.feature_github.presentation.screen.SplashScreen
import com.github.clientapplication.ui.theme.GitHubClientApplicationTheme
import com.github.clientapplication.githubrepos.utils.Constants.TAG
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubClientApplicationTheme {
                Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = NavRoutes.Splash.route) {
        composable(NavRoutes.Splash.route) {
            Log.d(TAG, NavRoutes.Splash.route)
            SplashScreen(navController = navController)
        }

        composable(NavRoutes.Main.route) {
            Log.d(TAG, NavRoutes.Main.route)
            MainScreen(navController = navController)
        }
    }
}