package com.poko.apps.user.application.dto.auth.request;

import com.poko.apps.user.domain.enums.user.UserRoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Optional;

public record SignupRequest(

    @NotBlank(message = "이메일: 이메일은 필수입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    String email,

    @NotBlank(message = "비밀번호: 비밀번호는 필수입니다.")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}|\\[\\]:\";'<>?,./]).{8,}$",
        message = "비밀번호는 최소 8자 이상이며, 영문자, 숫자, 특수문자를 포함해야 합니다."
    )
    String password,

    @NotBlank(message = "이름: 이름은 필수입니다.")
    String name,

    @NotBlank(message = "휴대전화번호: 휴대전화번호는 필수입니다.")
    @Pattern(
        regexp = "^\\d{11}$",
        message = "휴대전화번호는 숫자 11자리여야 합니다. 예: 01012345678")
    String phone,

    @Pattern(
        regexp = "^(|https?://.+)$",
        message = "빈 값이거나 http(s)://로 시작하는 유효한 URL이어야 합니다."
    )
    String profileImage,

    UserRoleType userRoleType
) {
    public String profileImageOrNull() {
        return Optional.ofNullable(profileImage)
            .filter(img -> !img.isBlank())
            .orElse(null);
    }
}
