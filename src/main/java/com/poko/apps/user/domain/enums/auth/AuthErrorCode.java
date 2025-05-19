package com.poko.apps.user.domain.enums.auth;

import com.poko.apps.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

  // 회원가입 관련
  DUPLICATE_PHONE(HttpStatus.CONFLICT.value(), "휴대전화번호: 이미 존재하는 휴대전화번호입니다.", HttpStatus.CONFLICT),
  DUPLICATE_EMAIL(HttpStatus.CONFLICT.value(), "이메일: 이미 존재하는 이메일입니다.", HttpStatus.CONFLICT),

  // 회원 관련
  USER_NOT_FOUND_BY_PHONE(HttpStatus.NOT_FOUND.value(), "존재하지 않는 휴대전화번호입니다.", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND_BY_EMAIL(HttpStatus.NOT_FOUND.value(), "존재하지 않는 이메일입니다.", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND.value(), "존재하지 않는 회원 ID 입니다.", HttpStatus.NOT_FOUND),

  // 로그인 관련
  INVALID_LOGIN(HttpStatus.BAD_REQUEST.value(), "아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
  INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(), "비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),

  // 토큰 관련
  TOKEN_EXPIRED(HttpStatus.BAD_REQUEST.value(), "해당 토큰이 만료되었습니다.", HttpStatus.BAD_REQUEST),
  INVALID_BEARER_TOKEN(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 JWT 토큰입니다.", HttpStatus.BAD_REQUEST),
  REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "리프레시 토큰을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  MALFORMED_TOKEN(HttpStatus.BAD_REQUEST.value(), "JWT 형식이 잘못되었습니다.", HttpStatus.BAD_REQUEST),
  TAMPERED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "JWT 서명이 위조되었거나 무결성이 손상되었습니다.", HttpStatus.UNAUTHORIZED),
  TOKEN_TOO_EARLY(HttpStatus.BAD_REQUEST.value(), "JWT 활성화 시간이 아직 되지 않았습니다.", HttpStatus.BAD_REQUEST);


  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
