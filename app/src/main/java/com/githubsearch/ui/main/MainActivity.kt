package com.githubsearch.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubsearch.R
import com.githubsearch.databinding.ActivityMainBinding
import com.githubsearch.ui.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class MainActivity : BaseActivity<MainEvent, MainUiModel>(), MainView,
    RepositoryAdapter.ClickListener {

    @Inject
    internal
    lateinit var presenter: MainPresenter

    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RepositoryAdapter
    private var canLoadMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaults(presenter.attach(this)).subscribe(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter = RepositoryAdapter(this)
        binding.list.layoutManager = LinearLayoutManager(baseContext)
        binding.list.adapter = adapter

        binding.search.setOnClickListener {
            binding.searchPhrase.text.let {
                if (it.isNotEmpty()) {
                    canLoadMore = false
                    adapter.clearItems()
                    sendEvent(MainEvent.search(it.toString()))
                    hideKeyboard(this)
                    binding.progressBar.visibility = VISIBLE
                    binding.emptyList.visibility = GONE
                } else {
                    Snackbar.make(binding.root, getString(R.string.search_empty), Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        binding.scroll.getViewTreeObserver()
            .addOnScrollChangedListener(object : ViewTreeObserver.OnScrollChangedListener {
                override fun onScrollChanged() {
                    val view: View =
                        binding.scroll.getChildAt(binding.scroll.getChildCount() - 1) as View

                    val diff: Int = view.getBottom() - (binding.scroll.getHeight() + binding.scroll
                        .getScrollY())

                    if (diff == 0 && canLoadMore) {
                        sendEvent(MainEvent.searchMore())
                    }
                }

            })
    }

    override fun sendEvent(event: MainEvent) {
        super.sendEvent(event)
        presenter.event(event)
    }

    override fun onNext(emptyUiModel: MainUiModel) {
        render(emptyUiModel)
    }

    override fun render(uiModel: MainUiModel) {
        when (uiModel.model) {
            MainUiModel.ON_ITEMS_LOAD -> {
                if(uiModel.responseData.items.isNotEmpty()) {
                    binding.emptyList.visibility = GONE
                    adapter.setItems(uiModel.responseData.items)
                    if (adapter.itemCount >= uiModel.responseData.total_count) {
                        binding.progressBar.visibility = GONE
                        canLoadMore = false
                    } else {
                        canLoadMore = true
                        binding.progressBar.visibility = VISIBLE
                    }
                } else {
                    binding.progressBar.visibility = GONE
                    binding.emptyList.visibility = VISIBLE
                }
            }
        }
        super.render(uiModel)
    }

    override protected fun onInternetConnected() {
        setDefaults(presenter.attach(this)).subscribe(this)
        if (lastEvent != null) {
            sendEvent(lastEvent)
            return
        }
    }

    override fun onClick(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}