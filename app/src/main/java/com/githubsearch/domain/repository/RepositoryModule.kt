package com.githubsearch.domain.repository

import dagger.Binds
import dagger.Module
import com.githubsearch.domain.DomainToolsModule

@Module(includes = arrayOf(DomainToolsModule::class))
abstract class RepositoryModule {

  @Binds protected abstract fun localRepository(
      localRepository: LocalRepositoryImpl): LocalRepository

  @Binds
  protected abstract fun remoteRepository(remoteRepository: RemoteRepositoryImpl): RemoteRepository

}