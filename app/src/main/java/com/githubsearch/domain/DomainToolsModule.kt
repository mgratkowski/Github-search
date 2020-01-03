package com.githubsearch.domain

import android.net.ConnectivityManager
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.githubsearch.BuildConfig
import com.githubsearch.domain.storage.StorageModule
import com.githubsearch.utility.MoshiDateAdapter
import com.githubsearch.utility.MyAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = arrayOf(StorageModule::class))
class DomainToolsModule {

  private val TIMEOUT = 20

  @Provides
  @Singleton internal fun provideRestAdapter(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    builder.baseUrl(BuildConfig.SERVER)
    return builder.build()
  }

  @Provides
  @Singleton internal fun provideHttpClient(connectivityManager : ConnectivityManager): OkHttpClient {
      val builder = OkHttpClient.Builder()
      if (BuildConfig.DEBUG) {
        builder.addNetworkInterceptor(StethoInterceptor())
      }
      builder.addInterceptor(NetworkInterceptor(connectivityManager))
      return builder.readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
          .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
          .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
          .build()
    }

  @Provides
  @Singleton internal fun provideDefaultMoshiBuilder(): Moshi.Builder {
    return Moshi.Builder().add(MoshiDateAdapter()).add(MyAdapterFactory.create())
  }

  @Provides
  @Singleton internal fun provideMoshi(moshiBuilder: Moshi.Builder): Moshi {
    return moshiBuilder.build()
  }
}
