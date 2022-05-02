package com.github.clientapplication.di.module

import com.apollographql.apollo3.ApolloClient
import com.github.clientapplication.feature_github.data.repository.RemoteRepositoryImpl
import com.github.clientapplication.feature_github.data.rest.GithubApi
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Singleton
    @Provides
    fun providesRepository(githubApi: GithubApi, apollo: ApolloClient): RemoteRepository =
        RemoteRepositoryImpl(githubApi, apollo)
}