package com.github.clientapplication.feature_github.domain.usecase

import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class GetReposLocal(
    private val repository: LocalRepository
) {
    suspend fun invoke(): List<RepoEntity> {
        return repository.getRepos()
    }
}
