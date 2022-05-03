package com.github.clientapplication

import com.github.clientapplication.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App: DaggerApplication() {
      private val applicationInjector = DaggerAppComponent
            .builder()
            .application(this)
            .build()
      override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector


}