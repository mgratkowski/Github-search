package com.githubsearch.application

import android.content.Context
import androidx.multidex.MultiDex
import com.githubsearch.di.AppComponent
import com.githubsearch.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class App : DaggerApplication {

  lateinit var applicationEnvironment: ApplicationEnvironment

  companion object {
    private lateinit var application: App

    fun getInstance() : App {
      return application
    }
  }

  constructor(){
    application = this
  }

  override fun onCreate() {
    super.onCreate()
    applicationEnvironment = ApplicationEnvironment(this)
    applicationEnvironment.init()
  }

  override fun attachBaseContext(base: Context?) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    var builder : AppComponent.Builder = DaggerAppComponent.builder()
    builder.seedInstance(this)
    return builder.build().androidInjector()
  }
}