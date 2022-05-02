package com.github.clientapplication.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.clientapplication.App
import com.github.clientapplication.di.score.DatabaseInfo
import com.github.clientapplication.di.score.PreferenceInfo
import com.github.clientapplication.feature_github.data.datasource.db.AppDatabase
import com.github.clientapplication.feature_github.data.datasource.db.dao.RepoDao
import com.github.clientapplication.utils.Constants
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
        return application
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context, @DatabaseInfo databaseName: String): AppDatabase {
        // return Room.databaseBuilder(context, AppDatabase.class, databaseName).addCallback(
        // in memory database
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            }
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): RepoDao {
        return database.repoDao()
    }
}