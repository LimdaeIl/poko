package com.poko.apps.common.domain.vo;

import com.poko.apps.common.domain.enums.UserRoleType;

public record CurrentUserInfo(
    Long userId,
    UserRoleType userRoleType
) {

  public static CurrentUserInfo of(Long userId, UserRoleType userRoleType) {
    return new CurrentUserInfo(userId, userRoleType);
  }

}
