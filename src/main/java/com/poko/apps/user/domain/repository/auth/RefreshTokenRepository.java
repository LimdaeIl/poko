package com.poko.apps.user.domain.repository.auth;

public interface RefreshTokenRepository {

  void saveRefreshToken(Long userId, String refreshToken, long ttlMillis);

}
