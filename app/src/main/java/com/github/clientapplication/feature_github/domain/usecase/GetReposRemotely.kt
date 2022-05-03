package com.github.clientapplication.feature_github.domain.usecase

import com.apollographql.apollo3.api.ApolloResponse
import com.github.clientapplication.GetRepositoriesQuery
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import com.github.clientapplication.feature_github.domain.repository.LocalRepository
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow

class GetReposRemotely(
    private val repository: RemoteRepository
) {
    suspend fun invoke(): ApolloResponse<GetRepositoriesQuery.Data> {
        return repository.getRepos()
    }
}