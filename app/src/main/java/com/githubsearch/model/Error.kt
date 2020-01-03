package com.githubsearch.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class Error(
    val msg: String
) : PaperParcelable {
  companion object {
    @JvmField val CREATOR = PaperParcelError.CREATOR
  }
}