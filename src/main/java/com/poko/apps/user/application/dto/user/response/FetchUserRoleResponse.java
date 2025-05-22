package com.poko.apps.user.application.dto.user.response;

import com.poko.apps.user.domain.enums.user.UserRoleType;

public record FetchUserRoleResponse(
    Long id,
    UserRoleType userRoleType
) {

  public static FetchUserRoleResponse of(Long id, UserRoleType userRoleType) {
    return new FetchUserRoleResponse(id, userRoleType);
  }

}
