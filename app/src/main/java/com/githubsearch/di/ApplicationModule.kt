package com.githubsearch.di

import android.content.ContentResolver
import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import com.githubsearch.application.App
import javax.inject.Singleton

@Module
class ApplicationModule {

  @Provides
  @Singleton
  internal fun provideAppContext(): Context {
    return App.getInstance()
  }

  @Provides
  @Singleton
  internal fun provideConnectivityManager(app: App): ConnectivityManager {
    return app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  }

  @Provides
  @Singleton
  internal fun provideContentResolver(app: App): ContentResolver {
    return app.contentResolver
  }

}