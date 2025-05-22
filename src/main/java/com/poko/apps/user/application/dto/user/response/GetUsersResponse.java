package com.poko.apps.user.application.dto.user.response;

import com.poko.apps.user.domain.enums.user.UserRoleType;
import java.time.LocalDateTime;

public record GetUsersResponse(
    Long id,
    String email,
    String name,
    String phone,
    String profileImage,
    UserRoleType userRoleType,
    Long createdBy,
    LocalDateTime createdAt,
    Long modifiedBy,
    LocalDateTime modifiedAt,
    Long deletedBy,
    LocalDateTime deletedAt
) {
}
