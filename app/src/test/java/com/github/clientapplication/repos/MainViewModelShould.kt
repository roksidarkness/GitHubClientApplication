package com.github.clientapplication.repos

import com.github.clientapplication.feature_github.domain.repository.LocalRepository
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository
import com.github.clientapplication.feature_github.domain.usecase.*
import com.github.clientapplication.feature_github.presentation.MainViewModel
import com.github.clientapplication.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.test.runBlockingTest
import com.github.clientapplication.utils.getValueForTest
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Test


class MainViewModelShould : BaseUnitTest() {

    private val reposLocal: LocalRepository = mock()
    private val reposRemote: RemoteRepository = mock()
    val getReposRemotely = GetReposRemotely(
        reposRemote
    )
    val getRepos = GetReposLocal(
        reposLocal
    )
    val getRepo = GetRepoLocal(
        reposLocal
    )
    val saveRepo = SaveRepoLocal(
        reposLocal
    )
    val addStar = AddStarLocal(
        reposLocal
    )
    val useCases = RepoUseCases(
        getReposLocal = getRepos,
        getRepoLocal = getRepo,
        getReposRemotely = getReposRemotely,
        addStar = addStar,
        saveRepo = saveRepo
    )

    @Test
    fun test_mainViewModel_shouldExist() = runBlockingTest {

        var viewModel = MainViewModel(
            useCases
        )

        assert(viewModel != null)
    }

    @Test
    fun test_mainViewModel_shouldRunAnReturnEmptyList() {
        var viewModel = MainViewModel(
            useCases
        )
        val repos = viewModel.state.value.repos

        assertEquals(repos.count(), 0)
    }

    @Test
    fun test_mainViewModel_shouldRunAnGetRepos() {
        var viewModel = MainViewModel(
            useCases
        )

        val repos = viewModel.stateRepos.value.repos
        assertEquals(repos.count(), 0)
    }

}