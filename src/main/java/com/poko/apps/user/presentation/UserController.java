package com.poko.apps.user.presentation;

import static com.poko.apps.user.domain.enums.user.UserSuccessCode.USER_GET_SUCCESS;
import static org.springframework.http.HttpStatus.OK;

import com.poko.apps.common.domain.vo.CurrentUserInfo;
import com.poko.apps.common.resolver.CurrentUser;
import com.poko.apps.common.util.response.ApiResponse;
import com.poko.apps.user.application.dto.auth.response.GetUserResponse;
import com.poko.apps.user.application.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

  private final UserService userService;

  // 자신의 회원 정보 조회
  @GetMapping("/profile")
  public ResponseEntity<ApiResponse<GetUserResponse>> getMyProfile(@CurrentUser CurrentUserInfo info) {

    return ResponseEntity
        .status(OK)
        .body(
            new ApiResponse<>(
                USER_GET_SUCCESS.getCode(),
                USER_GET_SUCCESS.getMessage(),
                userService.getMyProfile(info)
            )
        );
  }

  // 동적 회원 조회

  // 회원 아이디 수정

  // 회원 비밀번호 수정

  // 회원 프로필 수정

  // 회원 이름 수정

  // 회원 휴대전화번호 수정

  // 회원 권한 수정


}
