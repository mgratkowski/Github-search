package com.githubsearch.domain.repository

import dagger.Lazy
import dagger.Reusable
import javax.inject.Inject

@Reusable
class Repositories @Inject constructor(private val localRepository: Lazy<LocalRepository>,
    private val remoteRepository: Lazy<RemoteRepository>) {

  val remote: RemoteRepository
    get() = remoteRepository.get()

  val local: LocalRepository
    get() = localRepository.get()
}
