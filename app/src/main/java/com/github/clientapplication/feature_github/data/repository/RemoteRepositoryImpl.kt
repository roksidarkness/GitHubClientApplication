package com.github.clientapplication.feature_github.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.http.HttpHeader
import com.apollographql.apollo3.network.okHttpClient
import com.github.clientapplication.AddStarMutation
import com.github.clientapplication.GetRepositoriesQuery
import com.github.clientapplication.feature_github.data.model.AccessToken
import com.github.clientapplication.feature_github.data.pref.AppPreference
import com.github.clientapplication.feature_github.data.pref.PrefManager
import com.github.clientapplication.feature_github.data.rest.GithubApi
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository
import com.github.clientapplication.githubrepos.utils.Constants
import com.github.clientapplication.githubrepos.utils.Constants.AUTHORIZATION
import com.github.clientapplication.githubrepos.utils.Constants.BEARER
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val gethubApi: GithubApi, private val apollo: ApolloClient, private val pref: AppPreference): RemoteRepository{

    val token = pref.token
    val loggingInterceptor=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val builder = ApolloClient.Builder()
        .serverUrl(Constants.GITHUB_GRAPHQL_API_URL)
        .addHttpHeader(AUTHORIZATION, "$BEARER $token")
        .okHttpClient(client)
        .build()

    override suspend fun getRepos(): ApolloResponse<GetRepositoriesQuery.Data> {
        return builder.query(GetRepositoriesQuery()).execute()
    }

    override suspend fun getAccessToken(client: String, clientSecret: String, code: String): AccessToken {
        return gethubApi.getAccessToken(client, clientSecret, code)
    }

    override suspend fun addStart(id: String): ApolloResponse<AddStarMutation.Data> {
        return builder.mutation(AddStarMutation(id)).execute()
    }
}