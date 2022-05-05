package com.github.clientapplication.feature_github.domain.usecase

import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.domain.repository.LocalRepository

class SaveReposLocal(
    private val repository: LocalRepository
) {
    suspend operator fun invoke(repos: List<RepoEntity>) {
        return repository.saveRepos(repos)
    }
}