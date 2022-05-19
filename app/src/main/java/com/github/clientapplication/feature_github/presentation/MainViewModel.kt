package com.github.clientapplication.feature_github.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.github.clientapplication.AddStarMutation
import com.github.clientapplication.feature_github.data.model.Repo
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.domain.repository.toLocalRepo
import com.github.clientapplication.feature_github.domain.usecase.RepoUseCases
import com.github.clientapplication.githubrepos.utils.Constant.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repoUseCases: RepoUseCases) : ViewModel() {

    private var _repos = MutableLiveData<List<RepoEntity>>()
    val repos: LiveData<List<RepoEntity>> = _repos

    private var _repo = MutableLiveData<RepoEntity>()
    val repo: LiveData<RepoEntity> = _repo

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isLoadingRepo = MutableLiveData<Boolean>()
    val isLoadingRepo: LiveData<Boolean> = _isLoadingRepo

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val dataAddStar = MutableLiveData<ApolloResponse<AddStarMutation.Data>>()

    init {
        getRepo()
    }

    private fun getLocalRepos() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val listRepos = repoUseCases.getReposLocal.invoke()
                viewModelScope.launch(Dispatchers.Main) {
                    _repos.value = listRepos
                    _isLoading.value = false
                    repo.value?.let {
                        getLocalRepo(it.id)
                    }
                }
            }
        } catch (error: Exception) {
            Log.d(TAG, error.localizedMessage)
        }
    }

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
                Log.d(TAG, error.localizedMessage)
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
                val response: ApolloResponse<AddStarMutation.Data>? = repo.value?.let {
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
                _repo.value = repo
                _isLoadingRepo.value = false
            }
        } catch (error: Exception) {
            Log.d(TAG, error.localizedMessage)
        }
    }

    fun clearErrorMessage(){
        _errorMessage.value = ""
    }
}