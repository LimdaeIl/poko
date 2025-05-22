package com.poko.apps.user.domain.enums.user;

import static org.springframework.http.HttpStatus.OK;

import com.poko.apps.common.util.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserSuccessCode implements SuccessCode {

  USER_GET_SUCCESS(0, "회원 조회에 성공했습니다.", OK),
  USERS_GET_SUCCESS(0, "회원 목록 조회에 성공했습니다.", OK),
  USER_DELETE_BY_ID_SUCCESS(0, "회원 삭제에 성공했습니다.", OK),
  USER_PATCH_ROLE_SUCCESS(0, "회원 권한 수정에 성공했습니다.", OK),
  USER_PATCH_PASSWORD_SUCCESS(0, "회원 비밀번호 수정에 성공했습니다.", OK),
  SEND_CODE_SUCCESS(0, "이메일 인증 코드 전송에 성공했습니다.", OK),
  VERIFY_CODE_SUCCESS(0, "이메일 인증 코드가 동일합니다.", OK);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
