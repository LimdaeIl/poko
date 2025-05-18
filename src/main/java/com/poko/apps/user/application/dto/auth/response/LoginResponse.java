package com.poko.apps.user.application.dto.auth.response;

import lombok.Builder;

public record LoginResponse(
    IssueToken issueToken
) {

  public static LoginResponse of(String accessToken, String refreshToken) {
    return new LoginResponse(
        IssueToken.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build()
    );
  }

  @Builder
  record IssueToken(
      String accessToken,
      String refreshToken
  ) {
  }
}
