package com.github.clientapplication.feature_github.presentation

import android.content.Context
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.clientapplication.feature_github.presentation.navigation.NavRoutes
import com.github.clientapplication.feature_github.presentation.screen.DetailsScreen
import com.github.clientapplication.feature_github.presentation.screen.MainScreen
import com.github.clientapplication.feature_github.presentation.screen.SplashScreen
import com.github.clientapplication.githubrepos.utils.Constants.TAG
import com.github.clientapplication.githubrepos.utils.pxToDp
import com.github.clientapplication.ui.theme.GitHubClientApplicationTheme
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            GitHubClientApplicationTheme {
                Surface(color = Color(0xFFD1FFF9), modifier = Modifier.fillMaxSize()) {
                    Navigation(viewModel, calculeteScreenHeight(), calculeteScreenWidth())
                }
            }
        }
    }

    private fun calculeteScreenWidth(): Int{
        val mWidth = this.resources.displayMetrics.widthPixels
        return pxToDp(mWidth)

    }
    private fun calculeteScreenHeight(): Int{
        val mHeight = this.resources.displayMetrics.heightPixels
        return pxToDp(mHeight)
    }
}

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
                    state = viewModel.state,
                    effectFlow = viewModel.effects.receiveAsFlow(),
                    navController = navController
                )
        }

        composable(NavRoutes.RepoDetails.route) {
                DetailsScreen(
                    viewModel = viewModel,
                    state = viewModel.state,
                    effectFlow = viewModel.effects.receiveAsFlow()
                )
        }
    }
}