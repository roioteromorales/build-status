package com.roisoftstudio.buildstatus.logic.exception;

public class BuildNotFoundException extends RuntimeException {

  public BuildNotFoundException(String message) {
    super(message);
  }
}
