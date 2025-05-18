package com.poko.apps.user.application.dto.auth.response;

import com.poko.apps.user.domain.entity.User;
import com.poko.apps.user.domain.enums.user.UserRoleType;
import java.time.LocalDateTime;
import lombok.Builder;

public record SignupResponse(
    NewUser newUser
) {

  public static SignupResponse from(User user) {
    return new SignupResponse(
        NewUser.builder()
            .userId(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .profileImage(user.getProfileImage())
            .userRoleType(user.getUserRoleType())
            .createdAt(user.getCreatedAt())
            .build()
    );
  }

  @Builder
  record NewUser(
      Long userId,
      String email,
      String name,
      String profileImage,
      UserRoleType userRoleType,
      LocalDateTime createdAt
  ) {
  }
}
