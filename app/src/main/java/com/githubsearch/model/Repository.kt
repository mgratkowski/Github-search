package com.githubsearch.model

import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
data class Repository(
    val id: Int,
    val name: String,
    val description: String,
    val owner: Owner,
    val html_url: String
) : PaperParcelable {
  companion object {
    @JvmField val CREATOR = PaperParcelRepository.CREATOR
  }
}