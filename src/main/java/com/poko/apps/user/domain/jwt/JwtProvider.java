package com.poko.apps.user.domain.jwt;

import com.poko.apps.user.domain.enums.user.UserRoleType;

public interface JwtProvider {
  String createAccessToken(Long userId, UserRoleType userRoleType);
  String refreshAccessToken(Long userId);

  long getRefreshTokenExpire();

}
