package com.poko.apps.user.application.service;

import static com.poko.apps.common.exception.CommonErrorCode.INVALID_INPUT;

import com.poko.apps.common.exception.CustomException;
import com.poko.apps.user.application.dto.request.SignupRequest;
import com.poko.apps.user.application.dto.request.ExistsEmailRequest;
import com.poko.apps.user.application.dto.request.ExistsPhoneRequest;
import com.poko.apps.user.application.dto.response.SignupResponse;
import com.poko.apps.user.domain.entity.User;
import com.poko.apps.user.domain.enums.auth.AuthErrorCode;
import com.poko.apps.user.domain.enums.auth.AuthSuccessCode;
import com.poko.apps.user.domain.repository.user.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final AuthRepository authRepository;
  private final PasswordEncoder passwordEncoder;


  private boolean existsUserByEmail(String email) {
    return authRepository.existsUserByEmail(email);
  }

  private boolean existsUserByPhone(String phone) {
    return authRepository.existsUserByPhone(phone);
  }

  private User findUserByPhone(String phone) {
    return authRepository.findByPhone(phone)
        .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND_BY_PHONE));
  }

  private User findUserByEmail(String email) {
    return authRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND_BY_EMAIL));
  }

  @Transactional
  @Override
  public SignupResponse signup(SignupRequest request) {
    if (existsUserByEmail(request.email()) || existsUserByPhone(request.phone())) {
      throw new CustomException(INVALID_INPUT);
    }

    User user = User.builder()
        .email(request.email())
        .password(passwordEncoder.encode(request.password()))
        .name(request.name())
        .phone(request.phone())
        .profileImage(request.profileImageOrNull())
        .userRoleType(request.userRoleType())
        .build();

    User newUser = authRepository.save(user);

    return SignupResponse.from(newUser);
  }

  @Override
  public AuthSuccessCode existsEmail(ExistsEmailRequest request) {
    if (!existsUserByEmail(request.email())) {
      return AuthSuccessCode.EMAIL_AVAILABLE;
    }
    return AuthSuccessCode.EMAIL_DUPLICATE;
  }

  @Override
  public AuthSuccessCode existsPhone(ExistsPhoneRequest request) {
    if (!existsUserByPhone(request.phone())) {
      return AuthSuccessCode.PHONE_AVAILABLE;
    }
    return AuthSuccessCode.PHONE_DUPLICATE;
  }
}
