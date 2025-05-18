package com.poko.apps.user.application.service;

import static com.poko.apps.common.exception.CommonErrorCode.INVALID_INPUT;

import com.poko.apps.common.exception.CustomException;
import com.poko.apps.user.application.dto.auth.request.ExistsEmailRequest;
import com.poko.apps.user.application.dto.auth.request.ExistsPhoneRequest;
import com.poko.apps.user.application.dto.auth.request.LoginRequest;
import com.poko.apps.user.application.dto.auth.request.SignupRequest;
import com.poko.apps.user.application.dto.auth.response.LoginResponse;
import com.poko.apps.user.application.dto.auth.response.SignupResponse;
import com.poko.apps.user.domain.entity.User;
import com.poko.apps.user.domain.enums.auth.AuthErrorCode;
import com.poko.apps.user.domain.enums.auth.AuthSuccessCode;
import com.poko.apps.user.domain.jwt.JwtProvider;
import com.poko.apps.user.domain.repository.auth.AuthRepository;
import com.poko.apps.user.domain.repository.auth.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final AuthRepository authRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final RefreshTokenRepository refreshTokenRepository;


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

  private void passwordMatch(String password, User user) {
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new CustomException(AuthErrorCode.INVALID_PASSWORD);
    }
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

  @Transactional
  @Override
  public LoginResponse login(LoginRequest request) {
    User userByEmail = findUserByEmail(request.email());

    passwordMatch(request.password(), userByEmail);

    String accessToken = jwtProvider.createAccessToken(userByEmail.getId(), userByEmail.getUserRoleType());
    String refreshToken = jwtProvider.refreshAccessToken(userByEmail.getId());

    refreshTokenRepository.saveRefreshToken(userByEmail.getId(), refreshToken, jwtProvider.getRefreshTokenExpire());

    return LoginResponse.of(accessToken, refreshToken);
  }

  @Transactional
  @Override
  public void logout(String accessToken) {
    long remainingMillis = jwtProvider.getRemainingMillisByToken(accessToken);
    String jti = jwtProvider.getTokenId(accessToken);
    Long userId = jwtProvider.getUserIdByToken(accessToken);

    refreshTokenRepository.setTokenBlacklist(jti, remainingMillis);
    refreshTokenRepository.deleteRefreshToken(userId);
  }


}
