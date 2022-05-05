package com.github.clientapplication.di.module

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.github.clientapplication.App
import com.github.clientapplication.di.score.DatabaseInfo
import com.github.clientapplication.di.score.PreferenceInfo
import com.github.clientapplication.feature_github.data.datasource.db.AppDatabase
import com.github.clientapplication.feature_github.data.datasource.db.dao.RepoDao
import com.github.clientapplication.feature_github.data.pref.AppPreference
import com.github.clientapplication.feature_github.data.repository.LocalRepositoryImp
import com.github.clientapplication.feature_github.data.repository.RemoteRepositoryImpl
import com.github.clientapplication.feature_github.data.rest.GithubApi
import com.github.clientapplication.feature_github.domain.repository.LocalRepository
import com.github.clientapplication.feature_github.domain.repository.RemoteRepository
import com.github.clientapplication.feature_github.domain.usecase.*
import com.github.clientapplication.githubrepos.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object DataModule {

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return Constants.DATABASE_NAME
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return Constants.PREFERENCE_NAME
    }

    @Provides
    @Singleton
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    @Volatile
    private var INSTANCE: AppDatabase? = null

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context, @DatabaseInfo databaseName: String): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                databaseName
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): RepoDao {
        return database.repoDao()
    }

    @Singleton
    @Provides
    fun providesRepository(githubApi: GithubApi, apollo: ApolloClient, pref: AppPreference): RemoteRepository =
        RemoteRepositoryImpl(githubApi, apollo, pref)

    @Singleton
    @Provides
    fun providesLocalRepository(repoDao: RepoDao): LocalRepository =
        LocalRepositoryImp(repoDao)

    @Singleton
    @Provides
    fun provideRepoUseCase(remoteRepository: RemoteRepository, localRepository: LocalRepository): RepoUseCases {
        return RepoUseCases(
            getReposLocal = GetReposLocal(localRepository),
            getReposRemotely = GetReposRemotely(remoteRepository),
            getRepoLocal = GetRepoLocal(localRepository),
            addStar = AddStarRemotely(remoteRepository),
            saveRepos = SaveReposLocal(localRepository)
        )
    }
}