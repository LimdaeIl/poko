package com.poko.apps.user.application.service.user;


import static com.poko.apps.user.domain.enums.user.UserErrorCode.INVALID_PATCH_PASSWORD;
import static com.poko.apps.user.domain.enums.user.UserErrorCode.INVALID_PATCH_USER;
import static com.poko.apps.user.domain.enums.user.UserErrorCode.USER_NOT_FOUND_BY_EMAIL;
import static com.poko.apps.user.domain.enums.user.UserErrorCode.USER_NOT_FOUND_BY_ID;
import static com.poko.apps.user.domain.enums.user.UserErrorCode.USER_NOT_FOUND_BY_PHONE;
import static com.poko.apps.user.domain.enums.user.UserRoleType.ROLE_ADMIN;

import com.poko.apps.common.domain.vo.CurrentUserInfo;
import com.poko.apps.common.exception.CustomException;
import com.poko.apps.user.application.dto.user.request.FetchRoleRequest;
import com.poko.apps.user.application.dto.user.request.PatchUserPasswordRequest;
import com.poko.apps.user.application.dto.user.request.UserSearchCondition;
import com.poko.apps.user.application.dto.user.response.FetchUserRoleResponse;
import com.poko.apps.user.application.dto.user.response.GetUserResponse;
import com.poko.apps.user.application.dto.user.response.GetUsersResponse;
import com.poko.apps.user.application.dto.user.response.PatchUserEmailResponse;
import com.poko.apps.user.domain.entity.User;
import com.poko.apps.user.domain.repository.user.UserQueryRepository;
import com.poko.apps.user.domain.repository.user.UserRepository;
import com.poko.apps.user.presentation.PatchUserEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserQueryRepository userQueryRepository;
  private final PasswordEncoder passwordEncoder;

  private User findUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND_BY_ID));
  }

  private User findUserByPhone(String phone) {
    return userRepository.findByPhone(phone)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND_BY_PHONE));
  }

  private User findUserByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND_BY_EMAIL));
  }

  @Override
  public GetUserResponse getMyProfile(CurrentUserInfo info) {
    User userById = findUserById(info.userId());

    return GetUserResponse.from(userById);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<GetUsersResponse> getUsers(UserSearchCondition condition, Pageable pageable) {

    return userQueryRepository.getUsersByCondition(condition, pageable);
  }

  @Transactional
  @Override
  public PatchUserEmailResponse patchUserEmail(CurrentUserInfo info, Long id, PatchUserEmailRequest request) {
    isAdmin(info, id);

    User userById = findUserById(id);
    userById.fetchEmail(request.email());
    userRepository.save(userById);

    return PatchUserEmailResponse.from(userById);
  }

  @Transactional
  @Override
  public void patchUserPassword(CurrentUserInfo info, Long id, PatchUserPasswordRequest request) {
    isAdmin(info, id);
    User userById = findUserById(id);

    if (!passwordEncoder.matches(request.password(), userById.getPassword())) {
      throw new CustomException(INVALID_PATCH_PASSWORD);
    }

    userById.fetchPassword(passwordEncoder.encode(request.password()));
    userRepository.save(userById);
  }

  @Transactional
  @Override
  public FetchUserRoleResponse patchUserRole(CurrentUserInfo info, Long id, FetchRoleRequest request) {
    isAdmin(info, id);
    User userById = findUserById(id);

    userById.fetchRole(request.newUserRoleType());
    User save = userRepository.save(userById);

    return FetchUserRoleResponse.of(save.getId(), save.getUserRoleType());
  }

  private void isAdmin(CurrentUserInfo info, Long id) {
    boolean isAdmin = info.userRoleType().getName().equals(ROLE_ADMIN.getName());

    if (!isAdmin && !info.userId().equals(id)) {
      throw new CustomException(INVALID_PATCH_USER);
    }
  }
}
