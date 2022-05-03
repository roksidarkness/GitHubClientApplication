package com.github.clientapplication.feature_github.domain.usecase

import androidx.lifecycle.LiveData
import com.apollographql.apollo3.api.ApolloResponse
import com.github.clientapplication.GetRepositoriesQuery
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.domain.repository.LocalRepository

class GetRepoLocal (
    private val repository: LocalRepository
){
    suspend fun invoke(id: String): RepoEntity {
        return repository.getRepo(id)
    }
}