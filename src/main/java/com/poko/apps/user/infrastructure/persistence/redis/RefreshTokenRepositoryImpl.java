package com.poko.apps.user.infrastructure.persistence.redis;

import com.poko.apps.user.domain.repository.auth.RefreshTokenRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  private final RedisTemplate<String, String> redisTemplate;

  private static final String LOGOUT = "LOGOUT";

  @Override
  public String getEmailCode(String email) {
    String key = "EC:" + email;
    return redisTemplate.opsForValue().get(key);

  }

  @Override
  public void setEmailCode(String email, String code, Duration duration) {
    String key = "EC:" + email;
    redisTemplate.opsForValue().set(key, code, duration);
  }

  @Override
  public void saveRefreshToken(Long userId, String refreshToken, long ttlMillis) {
    String key = "RT:" + userId;
    redisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(ttlMillis)); // TODO: 트러블슈팅 TTL 작성
  }

  @Override
  public void setTokenBlacklist(String jti, long ttlMillis) {
    String key = "BL:" + jti;
    redisTemplate.opsForValue().set(key, LOGOUT, Duration.ofMillis(ttlMillis));
  }

  @Override
  public void deleteRefreshToken(Long userId) {
    String key = "RT:" + userId;
    redisTemplate.delete(key);
  }

}
