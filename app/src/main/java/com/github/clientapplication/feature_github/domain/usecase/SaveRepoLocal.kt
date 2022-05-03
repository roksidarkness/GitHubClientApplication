package com.github.clientapplication.feature_github.domain.usecase

import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.domain.repository.LocalRepository

class SaveRepoLocal(
    private val repository: LocalRepository
) {
    operator suspend fun invoke(repo: RepoEntity) {
        return repository.saveRepo(repo = repo)
    }
}