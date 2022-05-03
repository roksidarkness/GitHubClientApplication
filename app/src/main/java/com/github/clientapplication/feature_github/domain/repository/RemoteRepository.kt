package com.github.clientapplication.feature_github.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.github.clientapplication.GetRepositoriesQuery
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getRepos(): ApolloResponse<GetRepositoriesQuery.Data>
}