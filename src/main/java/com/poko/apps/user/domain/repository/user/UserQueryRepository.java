package com.poko.apps.user.domain.repository.user;

import com.poko.apps.user.application.dto.auth.request.UserSearchCondition;
import com.poko.apps.user.application.dto.auth.response.GetUsersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {

  Page<GetUsersResponse> getUsersByCondition(UserSearchCondition condition, Pageable pageable);

}
