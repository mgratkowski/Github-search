package com.githubsearch.domain.interactors

import com.githubsearch.di.SessionScope
import com.githubsearch.domain.repository.LocalRepository
import com.githubsearch.domain.repository.RemoteRepository
import com.githubsearch.domain.repository.Repositories
import com.githubsearch.model.ResponseData
import io.reactivex.Maybe
import javax.inject.Inject

@SessionScope
internal class MainInteractorImpl @Inject constructor(repositories: Repositories) : MainInteractor {

  private val local: LocalRepository
  private val remote: RemoteRepository

  init {
    this.local = repositories.local
    this.remote = repositories.remote
  }

  override fun getRepository(name: String, page : Int): Maybe<ResponseData> {
    return remote.getRepository(name, page)
  }
}