package com.github.clientapplication.di.module

import com.apollographql.apollo3.ApolloClient
import com.github.clientapplication.feature_github.data.repository.RemoteRepositoryImpl
import com.github.clientapplication.feature_github.data.rest.GithubApi
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository
import com.github.clientapplication.feature_github.domain.usecase.AddStar
import com.github.clientapplication.feature_github.domain.usecase.GetRepo
import com.github.clientapplication.feature_github.domain.usecase.GetRepos
import com.github.clientapplication.feature_github.domain.usecase.RepoUseCases
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Singleton
    @Provides
    fun providesRepository(githubApi: GithubApi, apollo: ApolloClient): RemoteRepository =
        RemoteRepositoryImpl(githubApi, apollo)

    @Singleton
    @Provides
    fun provideRepoUseCase(remoteRepository: RemoteRepository): RepoUseCases {
        return RepoUseCases(
            getRepos = GetRepos(remoteRepository),
            getRepo = GetRepo(remoteRepository),
            addStar = AddStar(remoteRepository)
        )
    }
}