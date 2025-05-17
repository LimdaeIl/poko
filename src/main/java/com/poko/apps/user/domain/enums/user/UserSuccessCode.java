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
  USER_PATCH_ROLE_BY_ID_SUCCESS(0, "회원 권한 수정에 성공했습니다.", OK);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
