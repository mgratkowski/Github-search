package com.githubsearch.domain.storage

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import com.githubsearch.application.App
import javax.inject.Singleton

@Module
class StorageModule {

  @Provides
  @Singleton internal fun provideStorage(application: App, moshi: Moshi): Storage {
    return Storage.getDefault(application, moshi)
  }
}
