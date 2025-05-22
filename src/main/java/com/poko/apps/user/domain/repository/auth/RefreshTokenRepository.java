package com.poko.apps.user.domain.repository.auth;

import java.time.Duration;

public interface RefreshTokenRepository {

  void setEmailCode(String email, String code, Duration duration);

  void saveRefreshToken(Long userId, String refreshToken, long ttlMillis);

  void setTokenBlacklist(String jti, long ttlMillis);

  void deleteRefreshToken(Long userId);

  String getEmailCode(String verifyCode);
}
