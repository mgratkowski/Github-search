package com.githubsearch.ui.base

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.githubsearch.R
import com.githubsearch.utility.errors.NoInternetExceptiom
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import icepick.Icepick
import icepick.State
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<E : BaseEvent, M : BaseUiModel> : RxAppCompatActivity(), Observer<M>, BaseView<M>, HasSupportFragmentInjector {
  protected var disposables = CompositeDisposable()

  @Inject
  internal
  lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  @State
  internal
  lateinit var lastUiModel: M

  protected lateinit var lastEvent: E
  private var progress: AlertDialog? = null
  private var alert: AlertDialog? = null
  private var noInternetAlert: AlertDialog? = null
  public var isOnCreate = false

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    isOnCreate = true
    Icepick.restoreInstanceState(this, savedInstanceState)
  }

  @CallSuper
  public override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    Icepick.saveInstanceState(this, outState)
  }

  @CallSuper
  override fun onDestroy() {
    super.onDestroy()
    if (!disposables.isDisposed) {
      disposables.dispose()
    }
  }

  @CallSuper
  override fun onSubscribe(d: Disposable) {
    disposables.add(d)
  }

  override fun onError(throwable: Throwable) {
    Timber.e(throwable, "onError() called")
    if (throwable.cause is NoInternetExceptiom) run {
      showNoInternetAlertDialog()
    }
  }

  override fun onComplete() {
  }

  @CallSuper
  override fun render(uiModel: M) {
  }

  override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
    return fragmentInjector
  }

  protected val isLastUiModel: Boolean
    get() = lastUiModel != null

  protected open fun sendEvent(event: E) {
    lastEvent = event
  }

  fun <Any> setDefaults(observable: Observable<Any>): ObservableSource<Any> {
    return observable.compose(bindToLifecycle()).observeOn(AndroidSchedulers.mainThread())
  }

  fun showNoInternetAlertDialog() {
    if (noInternetAlert == null || !noInternetAlert!!.isShowing()) {
      val builder = AlertDialog.Builder(this)
      builder.setMessage(getString(R.string.no_internet))
          .setCancelable(false)
          .setPositiveButton(getString(R.string.retry)) { dialog, id ->
            onInternetConnected()
            dialog.dismiss()
          }
          .setNegativeButton(getString(R.string.open_settings)
          ) { dialogInterface, i ->
            startActivityForResult(
                Intent(Settings.ACTION_SETTINGS), 0)
          }
      noInternetAlert = builder.create()
      noInternetAlert!!.show()
    }
  }

  protected abstract fun onInternetConnected()
}