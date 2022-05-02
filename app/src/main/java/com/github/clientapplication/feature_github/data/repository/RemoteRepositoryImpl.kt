package com.github.clientapplication.feature_github.data.repository

import com.apollographql.apollo3.ApolloClient
import com.github.clientapplication.feature_github.data.rest.GithubApi
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val gethubApi: GithubApi, private val apollo: ApolloClient): RemoteRepository{
}