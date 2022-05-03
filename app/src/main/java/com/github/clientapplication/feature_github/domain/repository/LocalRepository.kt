package com.github.clientapplication.feature_github.domain.repository

import androidx.lifecycle.LiveData
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getRepos(): Flow<List<RepoEntity>>
    suspend fun getRepo(id: String): RepoEntity
    suspend fun saveRepo(repo: RepoEntity)
}