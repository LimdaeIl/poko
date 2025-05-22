package com.poko.apps.user.application.dto.user.request;

import com.poko.apps.user.domain.enums.user.UserRoleType;

public record FetchRoleRequest(
    UserRoleType newUserRoleType
) {
}
