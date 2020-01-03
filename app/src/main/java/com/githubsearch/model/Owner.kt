package com.githubsearch.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class Owner(
    val login: String,
    val avatar_url: String
) : PaperParcelable {
  companion object {
    @JvmField val CREATOR = PaperParcelOwner.CREATOR
  }
}