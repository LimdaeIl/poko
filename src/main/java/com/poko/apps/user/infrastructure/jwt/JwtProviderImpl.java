package com.poko.apps.user.infrastructure.jwt;

import com.poko.apps.user.domain.enums.user.UserRoleType;
import com.poko.apps.user.domain.jwt.JwtProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
public class JwtProviderImpl implements JwtProvider {

  private final Key accessKey;
  private final Key refreshKey;
  private final SecretKey secretKey;

  @Value("${spring.application.name}")
  private String issuer;

  @Value("${jwt.access.expire}")
  private long accessTokenExpire;

  @Value("${jwt.refresh.expire}")
  private long refreshTokenExpire;

  @Value("${jwt.cookie.secure}")
  private boolean isCookieSecure;

  @Value("${jwt.cookie.httpOnly}")
  private boolean isCookieHttpOnly;

  @Value("${jwt.cookie.sameSite}")
  private String sameSite;

  @Value("${jwt.cookie.domain}")
  private String domain;

  @Value("${jwt.cookie.path}")
  private String path;

  public JwtProviderImpl(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.accessKey = Keys.hmacShaKeyFor(keyBytes);
    this.refreshKey = Keys.hmacShaKeyFor(keyBytes);
    this.secretKey = Keys.hmacShaKeyFor(keyBytes);
  }


  @Override
  public String createAccessToken(Long userId, UserRoleType userRoleType) {
    return Jwts.builder()
        .id(UUID.randomUUID().toString())
        .subject(userId.toString())
        .claim("USER_ROLE", userRoleType)
        .issuer(issuer)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + accessTokenExpire))
        .signWith(secretKey)
        .compact();
  }

  @Override
  public String refreshAccessToken(Long userId) {
    return Jwts.builder()
        .id(UUID.randomUUID().toString())
        .subject(userId.toString())
        .issuer(issuer)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + refreshTokenExpire))
        .signWith(secretKey)
        .compact();
  }
}
