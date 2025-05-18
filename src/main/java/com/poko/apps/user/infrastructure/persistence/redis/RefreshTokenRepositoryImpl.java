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

  @Override
  public void saveRefreshToken(Long userId, String refreshToken, long ttlMillis) {
    String key = "RT:" + userId;
    redisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(ttlMillis)); // TODO: 트러블슈팅 TTL 작성
  }
}
