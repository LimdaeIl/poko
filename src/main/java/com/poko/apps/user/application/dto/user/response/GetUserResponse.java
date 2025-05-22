package com.poko.apps.user.application.dto.user.response;

import com.poko.apps.user.domain.entity.User;
import com.poko.apps.user.domain.enums.user.UserRoleType;
import lombok.Builder;

public record GetUserResponse(
    MyProfile myProfile
) {

  public static GetUserResponse from(User user) {
    return new GetUserResponse(
        MyProfile.builder()
            .email(user.getEmail())
            .name(user.getName())
            .phone(user.getPhone())
            .profileImage(user.getProfileImage())
            .userRoleType(user.getUserRoleType())
            .build()
    );
  }

  @Builder
  record MyProfile(
      String email,
      String name,
      String phone,
      String profileImage,
      UserRoleType userRoleType) {
  }
}
