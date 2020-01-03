package com.githubsearch.domain

import android.net.ConnectivityManager
import com.githubsearch.utility.errors.NoInternetExceptiom
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException


internal class NetworkInterceptor(private val manager: ConnectivityManager) : Interceptor {

  private
  val isNetworkAvailable: Boolean
    get() {
      val networkInfo = manager.activeNetworkInfo
      Timber.d("isNetworkAvailable: []" + (networkInfo?.toString() ?: "empty"))
      return networkInfo != null
    }

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest = chain.request()
    try {
      if (isNetworkAvailable) return chain.proceed(originalRequest)
      Timber.d("intercept: [chain]")
    } catch (ignore: ConnectException) {
    }

    throw NoInternetExceptiom()
  }

}