package com.githubsearch.ui.main

import com.githubsearch.ui.base.BaseEvent
import com.githubsearch.utility.Constants.Companion.EMPTY_STRING
import paperparcel.PaperParcel

@PaperParcel
data class MainEvent(val event: Int = 0, val phrase : String = EMPTY_STRING) : BaseEvent {

  companion object {
    @JvmField
    val CREATOR = PaperParcelMainEvent.CREATOR

    const val SEARCH: Int = 1
    const val SEARCH_MORE: Int = 2

    fun search(phrase : String): MainEvent {
      return MainEvent(SEARCH, phrase)
    }

    fun searchMore(): MainEvent {
      return MainEvent(SEARCH_MORE)
    }
  }
}