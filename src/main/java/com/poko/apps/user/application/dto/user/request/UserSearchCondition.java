package com.poko.apps.user.application.dto.user.request;

import com.poko.apps.user.domain.enums.user.UserRoleType;
import java.time.LocalDateTime;

public record UserSearchCondition(
    Long id,
    String email,
    String name,
    String phone,
    UserRoleType userRoleType,
    Long createdBy,
    LocalDateTime createdFrom,
    LocalDateTime createdTo,
    Long modifiedBy,
    LocalDateTime modifiedFrom,
    LocalDateTime modifiedTo,
    Long deletedBy,
    LocalDateTime deletedFrom,
    LocalDateTime deletedTo
) {
}
