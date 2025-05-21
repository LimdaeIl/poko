package com.poko.apps.user.application.service.user;

import com.poko.apps.common.domain.vo.CurrentUserInfo;
import com.poko.apps.user.application.dto.auth.request.UserSearchCondition;
import com.poko.apps.user.application.dto.auth.response.GetUserResponse;
import com.poko.apps.user.application.dto.auth.response.GetUsersResponse;
import com.poko.apps.user.application.dto.auth.response.PatchUserEmailResponse;
import com.poko.apps.user.presentation.PatchUserEmailRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  GetUserResponse getMyProfile(CurrentUserInfo info);

  Page<GetUsersResponse> getUsers(UserSearchCondition condition, Pageable pageable);

  PatchUserEmailResponse patchUserEmail(CurrentUserInfo info, Long id, PatchUserEmailRequest request);
}
