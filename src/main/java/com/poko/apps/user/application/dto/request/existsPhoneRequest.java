package com.poko.apps.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record existsPhoneRequest(
    @NotBlank(message = "휴대전화번호: 휴대전화번호는 필수입니다.")
    @Pattern(
        regexp = "^\\d{11}$",
        message = "휴대전화번호는 숫자 11자리여야 합니다. 예: 01012345678")
    String phone
) {
}
