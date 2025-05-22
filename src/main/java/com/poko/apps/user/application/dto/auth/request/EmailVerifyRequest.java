package com.poko.apps.user.application.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record EmailVerifyRequest(
    @NotBlank(message = "이메일: 이메일은 필수입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    String email,

    @Min(100000)
    @Max(999999)
    Integer verifyCode
) {
}
