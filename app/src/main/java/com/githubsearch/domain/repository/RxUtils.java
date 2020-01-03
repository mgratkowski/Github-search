package com.githubsearch.domain.repository;

import androidx.annotation.NonNull;

import com.githubsearch.utility.errors.AppError;
import com.githubsearch.utility.errors.ResponseError;
import com.githubsearch.utility.errors.UnProcessableEntity;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;
import timber.log.Timber;

class RxUtils {

  private static final int NOT_AUTHORIZED = 401;
  private static final int API_ERROR = 406;
  private static final int LOGIN_ERROR = 404;
  private static final int UNPROCESSABLE_ENTITY = 422;
  private static final int CLOUDFLARE_ERROR = 522;

  private RxUtils() {
  }

  @NonNull
  static <T> ObservableTransformer<Result<T>, T> transformObservableResult() {

    return response -> response.map(RxUtils::returnResultOrError);
  }

  @NonNull static <T> SingleTransformer<Result<T>, T> transformSingleResult() {
    return response -> response.map(RxUtils::returnResultOrError);
  }

  @NonNull static <T> MaybeTransformer<Result<T>, T> transformMaybeResult() {
    return response -> response.map(RxUtils::returnResultOrError);
  }

  private static <T> T returnResultOrError(@NonNull Result<T> result) throws AppError {
    Response<T> response = result.response();
    if (!result.isError()) {
      if (response != null && response.isSuccessful()) {
        return response.body();
      } else {
        if (response != null) {
          return handleServerError(response);
        }
      }
    } else {
      logErrorMessage(result);
      if (response != null) {
        return handleServerError(response);
      }
    }
    throw new ResponseError(result.error());
  }

  @SuppressWarnings("ChainOfInstanceofChecks")
  private static <T> void logErrorMessage(@NonNull Result<T> result) {
    if (result.error() instanceof SocketTimeoutException) {
      Timber.w(result.error(), "socket timeout");
    } else if (result.error() instanceof IOException) {
      if (result.error() instanceof java.net.ConnectException) {
        Timber.w(result.error(), "connect exception");
      } else if (result.error() instanceof SocketTimeoutException) {
        Timber.w(result.error(), "socket timeout");
      } else {
        Timber.w(result.error(), "other network io exception");
        Response<T> response = result.response();
        if (response != null) {
          Timber.d("body: %s", response.raw().toString());
        }
      }
    } else {
      Timber.w(result.error(), "some other network exception");
    }
  }

  private static <T> T handleServerError(@NonNull Response<T> response) throws AppError {
    if (response.code() == UNPROCESSABLE_ENTITY) {
      ResponseBody errorBody = response.errorBody();
      if (errorBody != null) {
        throw new UnProcessableEntity(errorBody.source().toString());
      } else {
        throw new UnProcessableEntity("");
      }
    } else  {
      Timber.w("some strange unhandled case of response error");
      throw new ResponseError(response.code(), getBodyContent(response.errorBody()));
    }
  }

  private static String getBodyContent(ResponseBody body) {
    String bodyContent = "";
    if (body != null) {
      try {
        bodyContent = body.string();
      } catch (IOException e) {
        //ignored
      }
    }
    return bodyContent;
  }

  static Function<Observable<Throwable>, Observable<Long>> exponentialBackoff(int maxRetryCount,
      long delay, TimeUnit unit) {
    return errors -> errors.zipWith(Observable.range(1, maxRetryCount),
        (error, retryCount) -> retryCount)
        .flatMap(retryCount -> Observable.timer((long) Math.pow(delay, retryCount), unit));
  }
}
