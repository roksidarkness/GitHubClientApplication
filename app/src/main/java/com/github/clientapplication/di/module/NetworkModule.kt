package com.github.clientapplication.di.module

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.github.clientapplication.feature_github.data.pref.AppPreference
import com.github.clientapplication.feature_github.data.rest.GithubApi
import com.github.clientapplication.githubrepos.utils.Constant
import com.github.clientapplication.githubrepos.utils.Constant.API_URL
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    @Singleton
    @Provides
    fun providesOkhttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): GithubApi =
        retrofit.create(GithubApi::class.java)


    @Singleton
    @Provides
    fun provideApolloClient(client: OkHttpClient, pref: AppPreference): ApolloClient {
        val token = pref.token
        return ApolloClient.Builder()
            .serverUrl(Constant.GITHUB_GRAPHQL_API_URL)
            .addHttpHeader(Constant.AUTHORIZATION, "${Constant.BEARER} $token")
            .okHttpClient(client)
            .build()
    }
}