package com.github.clientapplication.repos

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.composable
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.github.clientapplication.feature_github.domain.repository.LocalRepository
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository
import com.github.clientapplication.feature_github.domain.usecase.*
import com.github.clientapplication.feature_github.presentation.MainViewModel
import com.github.clientapplication.feature_github.presentation.Navigation
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@LargeTest
class BaseUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val reposLocal: LocalRepository = com.nhaarman.mockitokotlin2.mock()
    private val reposRemote: RemoteRepository = com.nhaarman.mockitokotlin2.mock()
    val getReposRemotely = GetReposRemotely(
        reposRemote
    )
    val getRepos = GetReposLocal(
        reposLocal
    )
    val getRepo = GetRepoLocal(
        reposLocal
    )
    val saveRepo = SaveReposLocal(
        reposLocal
    )
    val addStar = AddStarRemotely(
        reposRemote
    )
    val useCases = RepoUseCases(
        getReposLocal = getRepos,
        getRepoLocal = getRepo,
        getReposRemotely = getReposRemotely,
        addStar = addStar,
        saveRepos = saveRepo
    )

    @Test
    fun simpleNavigationTest() {

        var viewModel = MainViewModel(
            useCases
        )

        composeTestRule.setContent {
            MaterialTheme {
                Navigation(viewModel = viewModel, height = 0, weight = 0)
            }
        }

}