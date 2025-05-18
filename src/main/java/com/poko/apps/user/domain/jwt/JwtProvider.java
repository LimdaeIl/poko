package com.poko.apps.user.domain.jwt;

import com.poko.apps.user.domain.enums.user.UserRoleType;
import io.jsonwebtoken.Claims;

public interface JwtProvider {
  String createAccessToken(Long userId, UserRoleType userRoleType);

  String refreshAccessToken(Long userId);

  long getRefreshTokenExpire();

  long getRemainingMillisByToken(String bearerToken);

  String getTokenId(String bearerToken);

  Long getUserIdByToken(String bearerToken);
}
