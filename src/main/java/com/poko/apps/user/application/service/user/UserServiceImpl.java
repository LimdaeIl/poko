package com.poko.apps.user.application.service.user;



import static com.poko.apps.user.domain.enums.user.UserErrorCode.USER_NOT_FOUND_BY_EMAIL;
import static com.poko.apps.user.domain.enums.user.UserErrorCode.USER_NOT_FOUND_BY_ID;
import static com.poko.apps.user.domain.enums.user.UserErrorCode.USER_NOT_FOUND_BY_PHONE;

import com.poko.apps.common.domain.vo.CurrentUserInfo;
import com.poko.apps.common.exception.CustomException;
import com.poko.apps.user.application.dto.auth.response.GetUserResponse;
import com.poko.apps.user.domain.entity.User;
import com.poko.apps.user.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

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

  

}
