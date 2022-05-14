package com.github.clientapplication.feature_github.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.github.clientapplication.AddStarMutation
import com.github.clientapplication.feature_github.data.model.Repo
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.domain.usecase.RepoUseCases
import com.github.clientapplication.feature_github.domain.repository.toLocalRepo
import com.github.clientapplication.githubrepos.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repoUseCases: RepoUseCases) : ViewModel() {

    private val _state = mutableStateOf(ReposState())
    val state: State<ReposState> = _state
    val stateRepos: State<ReposState>
        get() = _state

    val dataAddStar = MutableLiveData<ApolloResponse<AddStarMutation.Data>>()

    private val _errorMessage = mutableStateOf("")
    val errorMessage: String
        get() = _errorMessage.value

    init {
        getRepo()
    }


    private fun getLocalRepos() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val listRepos = repoUseCases.getReposLocal.invoke()
                viewModelScope.launch(Dispatchers.Main) {
                    _state.value = state.value.copy(
                        repos = listRepos,
                        isLoading = false
                    )
                    state.value.repo.value?.let {
                        getLocalRepo(it.id)
                    }
                }
            }
        } catch (err: Exception) {
            _errorMessage.value = err.message.toString()
        }
    }

    var effects = Channel<Effect>(Channel.UNLIMITED)
        private set

    private fun getRepo() {
        viewModelScope.launch {
            try {
                val response = repoUseCases.getReposRemotely.invoke()
                val remoteRepos = response.data?.viewer?.repositories?.nodes

                val repoList: MutableList<RepoEntity> = mutableListOf<RepoEntity>()
                remoteRepos?.forEach {
                    it?.let {
                        val repo = Repo(
                            it.id,
                            it.name,
                            it.shortDescriptionHTML.toString(),
                            it.primaryLanguage?.name ?: "",
                            it.stargazers.totalCount
                        )
                        repoList.add(repo.toLocalRepo())
                    }
                }
                saveRepos(repoList)
            } catch (error: Exception) {
                getLocalRepos()
                _errorMessage.value = error.message.toString()
            }
        }
    }

    private suspend fun saveRepos(repoList: MutableList<RepoEntity>) {
        viewModelScope.launch {
            repoUseCases.saveRepos(repoList)
            getLocalRepos()
        }
    }

    fun addStar() {
        viewModelScope.launch {
            try {
                val response: ApolloResponse<AddStarMutation.Data>? = state.value.repo.value?.let {
                    repoUseCases.addStar.invoke(it.id)
                }
                response?.let {
                    dataAddStar.postValue(it)
                    getRepo()
                }
            } catch (error: Exception) {
                _errorMessage.value = error.message.toString()
            }
        }
    }

    fun getLocalRepo(id: String) {
        try {

            viewModelScope.launch {
                val repo = repoUseCases.getRepoLocal.invoke(id)
                state.value.repo.value = repo
                _state.value = state.value.copy(
                    repo = state.value.repo,
                    isLoadingRepo = false
                )
            }
        } catch (error: Exception) {
            _errorMessage.value = error.message.toString()
        }
    }
}