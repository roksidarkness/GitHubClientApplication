package com.github.clientapplication.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.apollographql.apollo3.ApolloClient
import com.github.clientapplication.App
import com.github.clientapplication.di.score.DatabaseInfo
import com.github.clientapplication.di.score.PreferenceInfo
import com.github.clientapplication.feature_github.data.datasource.db.AppDatabase
import com.github.clientapplication.feature_github.data.datasource.db.dao.RepoDao
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

@Module(
    includes = [
        ViewModelModule::class,
        NetworkModule::class,
        DataModule::class
    ]
)
class AppModule {
}