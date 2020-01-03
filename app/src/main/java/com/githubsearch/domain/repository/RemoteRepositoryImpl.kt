package com.githubsearch.domain.repository

import com.githubsearch.domain.repository.RemoteRepository.RemoteService
import com.githubsearch.model.ResponseData
import dagger.Reusable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject


@Reusable
class RemoteRepositoryImpl @Inject internal constructor(
    retrofit: Retrofit,
    repositories: Repositories
) : RemoteRepository {

    override lateinit var service: RemoteRepository.RemoteService

    init {
        this.service = retrofit.create(RemoteService::class.java!!)
    }

    override fun getRepository(name: String, page : Int): Maybe<ResponseData> {
        return service.getRepository("topic:" + name, page).subscribeOn(Schedulers.io())
            .compose(RxUtils.transformMaybeResult())
    }

}
