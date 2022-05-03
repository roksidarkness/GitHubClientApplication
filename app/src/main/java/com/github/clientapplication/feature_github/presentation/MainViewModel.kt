package com.github.clientapplication.feature_github.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.github.clientapplication.GetRepositoriesQuery
import com.github.clientapplication.feature_github.data.model.Repo
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.domain.usecase.RepoUseCases
import com.github.clientapplication.githubrepos.utils.Constants
import com.github.clientapplication.feature_github.domain.repository.toLocalRepo
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repoUseCases: RepoUseCases) : ViewModel(){

    private var getReposJob: Job? = null
    private val _state = mutableStateOf(ReposState())
    val state: State<ReposState> = _state
    val stateRepos: State<ReposState>
        get() = _state

    val _dataRemoteRepo = MutableLiveData<ApolloResponse<GetRepositoriesQuery.Data>>()

    val _dataRepo = MutableLiveData<MutableList<Repo>>()

    init{
        getRepo()
    }

     fun getLocalRepos(){
        getReposJob?.cancel()
        getReposJob = repoUseCases.getReposLocal().onEach { repos->
            _state.value = state.value.copy(
                repos = repos
            )
        }.launchIn(viewModelScope)
    }

    private fun getRepo() {
        viewModelScope.launch {
            try {
                val response = repoUseCases.getReposRemotely.invoke()
                _dataRemoteRepo.postValue(response)

                viewModelScope.launch {
                    val remoteRepos = response.data?.viewer?.repositories?.nodes
                    Log.d(Constants.TAG, "Remote repos: "+  remoteRepos.toString())
                    val repoList: MutableList<Repo> = mutableListOf<Repo>()

                    remoteRepos?.forEach {
                        it?.let {
                            val repo = Repo(it.id, it.name)
                            repoList.add(repo)
                            _dataRepo.postValue(repoList)
                            var repoLocal = repo.toLocalRepo()
                            saveRepos(repoLocal)
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun saveRepos(repo: RepoEntity){
        repoUseCases.saveRepo(repo = repo)
    }
}