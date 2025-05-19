package com.poko.apps.user.application.service.user;

import com.poko.apps.common.domain.vo.CurrentUserInfo;
import com.poko.apps.user.application.dto.auth.response.GetUserResponse;

public interface UserService {

  GetUserResponse getMyProfile(CurrentUserInfo info);
}
