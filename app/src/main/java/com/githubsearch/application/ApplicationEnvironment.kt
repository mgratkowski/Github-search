package com.githubsearch.application

import com.facebook.stetho.Stetho
import com.githubsearch.BuildConfig
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.CompletableSource
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class ApplicationEnvironment : CompletableObserver {

  companion object {
    var ONE_FRAME: Int = 16
  }

  lateinit var app: App

   constructor(app: App) {
    this.app = app
  }

  internal fun init() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    } else {
    }

    Completable.merge(getTasks()).subscribe(this)
  }

  private fun getTasks(): Iterable<CompletableSource> {
    val sources = ArrayList<CompletableSource>(5)
    if (BuildConfig.DEBUG) {
      sources.add(loadStetho())
    }
    return sources
  }

  private fun loadStetho(): CompletableSource {
    return waitOneFrame().andThen({ cs: CompletableObserver ->
      Timber.d("ASd")
      Stetho.initializeWithDefaults(app) })
  }


  private fun waitOneFrame(): Completable {
    return Completable.timer(ONE_FRAME.toLong(), TimeUnit.MILLISECONDS)
  }

  override fun onSubscribe(d: Disposable) {
  }

  override fun onComplete() {
    Timber.d("delayed loading finished")
  }

  override fun onError(e: Throwable) {
    Timber.e(e, "onError() called")
  }
}