package com.githubsearch.domain.interactors

import com.githubsearch.model.ResponseData
import io.reactivex.Maybe

interface MainInteractor {
  fun getRepository(name: String, page : Int): Maybe<ResponseData>
}
