package com.github.clientapplication.feature_github.domain.repository

import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getRepos(): Flow<List<RepoEntity>>
    fun saveRepo(repo: RepoEntity)
}