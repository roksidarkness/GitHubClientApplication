package com.github.clientapplication.feature_github.presentation.navigation

sealed class NavRoutes(val route: String) {
    object Splash: NavRoutes("splash_screen")
    object Main: NavRoutes("main_screen")
    object RepoDetails : NavRoutes("repo_details_screen/{repoId}") {
        fun passRepoId(repoId: String) = "repo_details_screen/$repoId"
    }
}