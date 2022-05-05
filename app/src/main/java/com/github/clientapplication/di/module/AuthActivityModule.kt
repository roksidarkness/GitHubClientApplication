package com.github.clientapplication.di.module

import com.github.clientapplication.feature_github.presentation.auth.AuthActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class AuthActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): AuthActivity
}