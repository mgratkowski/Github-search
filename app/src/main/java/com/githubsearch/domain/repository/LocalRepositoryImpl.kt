package com.githubsearch.domain.repository

import com.githubsearch.domain.storage.Storage
import dagger.Reusable
import javax.inject.Inject

@Reusable
class LocalRepositoryImpl @Inject internal constructor(
    private val storage: Storage) : LocalRepository {

}