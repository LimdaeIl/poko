package com.poko.apps.user.presentation;

import static com.poko.apps.common.domain.enums.UserRoleType.ROLE_ADMIN;
import static com.poko.apps.common.domain.enums.UserRoleType.ROLE_DELIVERY;
import static com.poko.apps.common.domain.enums.UserRoleType.ROLE_STORE;
import static com.poko.apps.common.domain.enums.UserRoleType.ROLE_USER;
import static com.poko.apps.user.domain.enums.user.UserSuccessCode.USERS_GET_SUCCESS;
import static com.poko.apps.user.domain.enums.user.UserSuccessCode.USER_GET_SUCCESS;
import static com.poko.apps.user.domain.enums.user.UserSuccessCode.USER_PATCH_PASSWORD_SUCCESS;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.OK;

import com.poko.apps.common.aop.CustomPreAuthorize;
import com.poko.apps.common.domain.vo.CurrentUserInfo;
import com.poko.apps.common.resolver.CurrentUser;
import com.poko.apps.common.util.response.ApiResponse;
import com.poko.apps.user.application.dto.user.request.PatchUserPasswordRequest;
import com.poko.apps.user.application.dto.user.request.UserSearchCondition;
import com.poko.apps.user.application.dto.user.response.GetUserResponse;
import com.poko.apps.user.application.dto.user.response.GetUsersResponse;
import com.poko.apps.user.application.dto.user.response.PatchUserEmailResponse;
import com.poko.apps.user.application.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

  private final UserService userService;

  @CustomPreAuthorize(userRoleType = {ROLE_ADMIN, ROLE_USER, ROLE_DELIVERY, ROLE_STORE})
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

  @CustomPreAuthorize(userRoleType = {ROLE_ADMIN})
  @GetMapping
  public ResponseEntity<ApiResponse<Page<GetUsersResponse>>> getUsers(
      @ParameterObject UserSearchCondition condition,
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = DESC),
      }) Pageable pageable
  ) {
    return ResponseEntity
        .status(OK)
        .body(
            new ApiResponse<>(
                USERS_GET_SUCCESS.getCode(),
                USERS_GET_SUCCESS.getMessage(),
                userService.getUsers(condition, pageable)
            )
        );
  }

  // 회원 아이디 수정
  @CustomPreAuthorize(userRoleType = {ROLE_ADMIN, ROLE_USER, ROLE_DELIVERY, ROLE_STORE})
  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<PatchUserEmailResponse>> patchUserEmail(
      @CurrentUser CurrentUserInfo info,
      @PathVariable Long id,
      @RequestBody PatchUserEmailRequest request
  ) {
    return ResponseEntity
        .status(OK)
        .body(
            new ApiResponse<>(
                USERS_GET_SUCCESS.getCode(),
                USERS_GET_SUCCESS.getMessage(),
                userService.patchUserEmail(info, id, request)
            )
        );
  }

  // 회원 비밀번호 수정
  @CustomPreAuthorize(userRoleType = {ROLE_ADMIN, ROLE_USER, ROLE_DELIVERY, ROLE_STORE})
  @PatchMapping("/{id}/password")
  public ResponseEntity<ApiResponse<Void>> patchUserPassword(
      @CurrentUser CurrentUserInfo info,
      @PathVariable Long id,
      @RequestBody PatchUserPasswordRequest request
  ) {
    userService.patchUserPassword(info, id, request);

    return ResponseEntity
        .status(OK)
        .body(
            new ApiResponse<>(
                USER_PATCH_PASSWORD_SUCCESS.getCode(),
                USER_PATCH_PASSWORD_SUCCESS.getMessage(),
                null
            )
        );
  }

  // 회원 프로필 수정

  // 회원 이름 수정

  // 회원 휴대전화번호 수정

  // 회원 권한 수정


}
