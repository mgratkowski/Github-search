package com.githubsearch.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import com.githubsearch.application.App
import com.githubsearch.domain.interactors.DomainInteractorsModule

@SuppressWarnings("StaticMethodOnlyUsedInOneClass", "squid:S1118", "squid:S1610")
@Module(subcomponents = arrayOf(SessionModule.SessionComponent::class))
internal abstract class SessionModule {
  @Module
  companion object {
    @Provides
    @JvmStatic
    fun sessionComponentBuilder(builder: SessionComponent.Builder): SessionComponent {
      return builder.build()
    }
  }

  @Binds
  internal abstract fun injector(component: SessionComponent): AndroidInjector<App>

  @SessionScope
  @Subcomponent(
      modules = arrayOf(DomainInteractorsModule::class, ActivitiesModule::class, AndroidSupportInjectionModule::class))
  interface SessionComponent : AndroidInjector<App> {
    @Subcomponent.Builder
    abstract class Builder {
      abstract fun build(): SessionComponent
    }
  }
}