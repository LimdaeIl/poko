package com.poko.apps.common.util.response;

import org.springframework.http.HttpStatus;

public interface SuccessCode {
  Integer getCode();
  String getMessage();
  HttpStatus getHttpStatus();
}
