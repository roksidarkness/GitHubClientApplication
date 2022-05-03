package com.github.clientapplication.feature_github.presentation

import com.github.clientapplication.feature_github.data.model.entity.RepoEntity

data class ReposState(
    val repos : List<RepoEntity> = emptyList()
)