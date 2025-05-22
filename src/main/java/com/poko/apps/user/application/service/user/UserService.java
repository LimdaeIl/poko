package com.poko.apps.user.application.service.user;

import com.poko.apps.common.domain.vo.CurrentUserInfo;
import com.poko.apps.user.application.dto.user.request.PatchUserPasswordRequest;
import com.poko.apps.user.application.dto.user.request.UserSearchCondition;
import com.poko.apps.user.application.dto.user.response.GetUserResponse;
import com.poko.apps.user.application.dto.user.response.GetUsersResponse;
import com.poko.apps.user.application.dto.user.response.PatchUserEmailResponse;
import com.poko.apps.user.presentation.PatchUserEmailRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  GetUserResponse getMyProfile(CurrentUserInfo info);

  Page<GetUsersResponse> getUsers(UserSearchCondition condition, Pageable pageable);

  PatchUserEmailResponse patchUserEmail(CurrentUserInfo info, Long id, PatchUserEmailRequest request);

  void patchUserPassword(CurrentUserInfo info, Long id, PatchUserPasswordRequest request);
}
