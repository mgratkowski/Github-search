package com.githubsearch.utility.errors;

public class MethodNotImplemented extends Throwable {

  @Override public String getMessage() {
    return "Method not implemented";
  }
}
