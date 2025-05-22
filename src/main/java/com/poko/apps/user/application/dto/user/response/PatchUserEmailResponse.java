package com.poko.apps.user.application.dto.user.response;

import com.poko.apps.user.domain.entity.User;
import lombok.Builder;

public record PatchUserEmailResponse(
    NewEmail newEmail
) {

  public static PatchUserEmailResponse from(User user) {
    return new PatchUserEmailResponse(
        NewEmail.builder()
            .id(user.getId())
            .email(user.getEmail())
            .build()
    );
  }

  @Builder
  record NewEmail(
      Long id,
      String email
  ) {
  }
}
