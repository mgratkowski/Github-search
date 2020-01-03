package com.githubsearch.domain.interactors

import dagger.Binds
import dagger.Module

@Module
abstract class DomainInteractorsModule {
  @Binds
  internal abstract fun provideMainInteractor(
      mainInteractor: MainInteractorImpl): MainInteractor
}
