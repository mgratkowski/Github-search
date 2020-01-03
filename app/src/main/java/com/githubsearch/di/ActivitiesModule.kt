package com.githubsearch.di

import com.githubsearch.ui.main.MainActivity
import com.githubsearch.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class ActivitiesModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
  internal abstract fun mainActivity(): MainActivity
}
