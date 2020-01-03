package com.githubsearch.utility;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import javax.annotation.Nullable;

//@MoshiAdapterFactory(nullSafe = true)
public abstract class MyAdapterFactory
    implements JsonAdapter.Factory {

  public static JsonAdapter.Factory create() {
    return new JsonAdapter.Factory() {
      @Nullable @Override
      public JsonAdapter<?> create(Type type, Set<? extends Annotation> annotations, Moshi moshi) {
        return null;
      }
    };
  }
}
