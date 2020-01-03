package com.githubsearch.ui.main

import com.githubsearch.domain.interactors.MainInteractor
import com.githubsearch.ui.base.BasePresenter
import com.githubsearch.utility.Constants
import com.githubsearch.utility.Constants.Companion.EMPTY_STRING
import io.reactivex.Observable
import io.reactivex.ObservableSource
import timber.log.Timber
import javax.inject.Inject

class MainPresenter : BasePresenter<MainView, MainEvent, MainUiModel> {
    lateinit var interactor: MainInteractor
    private var page = 1
    private var phrase = EMPTY_STRING

    @Inject
    constructor(interactor: MainInteractor) {
        this.interactor = interactor
    }

    public override fun attach(view: MainView): Observable<MainUiModel> {
        return super.attach(view)
    }

    override fun detach() {
        super.detach()
    }

    override fun getModel(): Observable<MainUiModel> {
        return events.flatMap(this::onEvent)
    }

    private fun onEvent(event: MainEvent): ObservableSource<MainUiModel> {
        when (event.event) {
            MainEvent.SEARCH -> {
                page = 1
                this.phrase = event.phrase
                return interactor.getRepository(event.phrase, page).map {
                    MainUiModel.onLoadItems(it)
                }.toObservable()
            }
            MainEvent.SEARCH_MORE -> {
                page++
                return interactor.getRepository(phrase, page).map {
                    MainUiModel.onLoadItems(it)
                }.toObservable()
            }
            else -> {
                Timber.e("event %s unhandled", event)
                return Observable.error<MainUiModel>(Constants.METHOD_NOT_IMPLEMENTED)
            }
        }
    }
}