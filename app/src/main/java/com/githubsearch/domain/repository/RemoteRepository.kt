package com.githubsearch.domain.repository

import com.githubsearch.model.ResponseData
import io.reactivex.Maybe
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteRepository {

    val service: RemoteService

    fun getRepository(name: String, page : Int): Maybe<ResponseData>

    interface RemoteService {
        @GET("search/repositories")
        fun getRepository(@Query("q") name: String, @Query("page") page: Int): Maybe<Result<ResponseData>>
    }
}
