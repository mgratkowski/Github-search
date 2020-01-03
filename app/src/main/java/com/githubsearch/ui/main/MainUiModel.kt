package com.githubsearch.ui.main

import com.githubsearch.model.ResponseData
import com.githubsearch.ui.base.BaseUiModel
import paperparcel.PaperParcel

@PaperParcel
data class MainUiModel(
    val model: Int = BaseUiModel.INVALID,
    val responseData: ResponseData = ResponseData()
) : BaseUiModel {

    companion object {
        @JvmField
        val CREATOR = PaperParcelMainUiModel.CREATOR

        const val ON_ITEMS_LOAD: Int = 1
        const val ON_ERROR: Int = 2

        fun onLoadItems(responseData: ResponseData): MainUiModel {
            return MainUiModel(ON_ITEMS_LOAD, responseData)
        }

        fun onError(): MainUiModel {
            return MainUiModel(ON_ERROR)
        }
    }
}