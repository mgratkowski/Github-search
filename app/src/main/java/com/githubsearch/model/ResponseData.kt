package com.githubsearch.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable
import java.util.*

@PaperParcel
data class ResponseData(
    val total_count: Int = 0,
    val items: List<Repository> = Collections.emptyList()
) : PaperParcelable {
  companion object {
    @JvmField val CREATOR = PaperParcelResponseData.CREATOR
  }
}