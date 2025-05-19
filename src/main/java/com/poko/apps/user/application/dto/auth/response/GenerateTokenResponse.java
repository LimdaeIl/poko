package com.poko.apps.user.application.dto.auth.response;

import lombok.Builder;

public record GenerateTokenResponse(
    GenerateToken generateToken
) {

  public static GenerateTokenResponse of(String accessToken, String refreshToken) {
    return new GenerateTokenResponse(
        GenerateToken.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build()
    );
  }

  @Builder
  record GenerateToken(
      String accessToken,
      String refreshToken
  ) {
  }
}
