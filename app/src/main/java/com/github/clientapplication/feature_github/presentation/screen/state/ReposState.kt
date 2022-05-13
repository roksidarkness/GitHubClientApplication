package com.github.clientapplication.feature_github.presentation

import androidx.lifecycle.MutableLiveData
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity

data class ReposState(
    val repos: List<RepoEntity> = emptyList(),
    val isLoading: Boolean = true,
    var repo: MutableLiveData<RepoEntity> = MutableLiveData<RepoEntity>(),
    val isLoadingRepo: Boolean = true,
)

sealed class Effect {
    object DataWasLoaded : Effect()
}

