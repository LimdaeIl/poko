package com.poko.apps.user.domain.enums.user;

import com.poko.apps.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCode {

  USER_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND.value(), "회원 ID를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND_BY_PHONE(HttpStatus.NOT_FOUND.value(), "회원 휴대전화번호를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND_BY_EMAIL(HttpStatus.NOT_FOUND.value(), "회원 이메일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  USER_GET_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "해당 회원 정보를 조회할 권한이 없습니다.", HttpStatus.FORBIDDEN),
  USER_ROLE_NOT_VALID(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 회원 권한입니다.", HttpStatus.BAD_REQUEST),
  USER_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "이미 존재하는 회원입니다.", HttpStatus.CONFLICT),
  USER_SIGNUP_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원가입에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  USER_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원 정보 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  USER_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원 삭제에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
