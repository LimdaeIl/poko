package com.poko.apps.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
  Integer getCode();
  String getMessage();
  HttpStatus getHttpStatus();
}
