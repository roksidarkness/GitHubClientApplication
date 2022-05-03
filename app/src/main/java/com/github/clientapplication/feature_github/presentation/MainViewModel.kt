package com.github.clientapplication.feature_github.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.clientapplication.feature_github.domain.usecase.RepoUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repoUseCases: RepoUseCases) : ViewModel(){

    private var getReposJob: Job? = null
    private val _state = mutableStateOf(ReposState())
    val state: State<ReposState> = _state

    private fun getRepos(){
        getReposJob?.cancel()
        getReposJob = repoUseCases.getReposLocal().onEach { repos->
            _state.value = state.value.copy(
                repos = repos
            )
        }.launchIn(viewModelScope)
    }
}