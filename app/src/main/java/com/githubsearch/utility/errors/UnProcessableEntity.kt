package com.githubsearch.utility.errors

import com.squareup.moshi.Moshi
import java.io.IOException

class UnProcessableEntity(private val bodyContent: String) : AppError() {

  @Throws(IOException::class)
  fun <T> getBodyContent(moshi: Moshi, classType: Class<T>): T? {
    return moshi.adapter(classType).fromJson(bodyContent)
  }
}
