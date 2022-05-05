package com.github.clientapplication.feature_github.domain.usecase

import com.apollographql.apollo3.api.ApolloResponse
import com.github.clientapplication.AddStarMutation
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository

class AddStarRemotely (
    private val repository: RemoteRepository
){
    suspend fun invoke(id: String): ApolloResponse<AddStarMutation.Data> {
        return repository.addStart(id)
    }
}