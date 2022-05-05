package com.github.clientapplication.feature_github.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.github.clientapplication.AddStarMutation
import com.github.clientapplication.GetRepositoriesQuery
import com.github.clientapplication.feature_github.data.model.AccessToken
import com.github.clientapplication.feature_github.data.model.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    suspend fun getRepos(): ApolloResponse<GetRepositoriesQuery.Data>

    suspend fun getAccessToken(client: String, clientSecret: String, code: String): AccessToken

    suspend fun addStart(id: String): ApolloResponse<AddStarMutation.Data>
}