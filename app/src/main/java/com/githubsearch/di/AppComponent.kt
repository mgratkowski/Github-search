package com.githubsearch.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import com.githubsearch.application.App
import com.githubsearch.domain.DomainToolsModule
import com.githubsearch.domain.repository.RepositoryModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
    DomainToolsModule::class, RepositoryModule::class, AndroidSupportInjectionModule::class,
    SessionModule::class, ApplicationModule::class))
interface AppComponent : AndroidInjector<App> {

  fun androidInjector(): AndroidInjector<App>

  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<App>() {
    abstract override fun build(): AppComponent
  }
}