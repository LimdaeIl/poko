package com.poko.apps.user.domain.enums.auth;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.OK;

import com.poko.apps.common.util.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthSuccessCode implements SuccessCode {

  USER_SIGNUP_SUCCESS(0, "회원가입이 성공적으로 완료되었습니다.", OK),
  USER_LOGIN_SUCCESS(0, "로그인이 성공적으로 완료되었습니다.", OK),
  USER_LOGOUT_SUCCESS(0, "로그아웃이 성공적으로 완료되었습니다.", OK),
  USER_TOKEN_GENERATION_SUCCESS(0, "토큰이 성공적으로 발급되었습니다.", OK),
  USER_TOKEN_VALIDATION_SUCCESS(0, "토큰이 유효합니다.", OK),

  EMAIL_AVAILABLE(0, "이메일 사용 가능합니다.", OK),
  EMAIL_DUPLICATE(0, "이미 사용 중인 이메일입니다.", OK),

  PHONE_AVAILABLE(0, "휴대전화번호 사용 가능", OK),
  PHONE_DUPLICATE(0, "이미 사용 중인 휴대전화번호입니다.", OK);


  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus; // 200

}
