package com.poko.apps.user.application.service.auth;

import static com.poko.apps.common.exception.CommonErrorCode.INVALID_INPUT;
import static com.poko.apps.user.domain.enums.auth.AuthErrorCode.INVALID_BEARER_TOKEN;
import static com.poko.apps.user.domain.enums.auth.AuthErrorCode.INVALID_PASSWORD;
import static com.poko.apps.user.domain.enums.auth.AuthErrorCode.USER_NOT_FOUND_BY_EMAIL;
import static com.poko.apps.user.domain.enums.auth.AuthErrorCode.USER_NOT_FOUND_BY_ID;
import static com.poko.apps.user.domain.enums.auth.AuthErrorCode.USER_NOT_FOUND_BY_PHONE;

import com.poko.apps.common.exception.CustomException;
import com.poko.apps.user.application.dto.auth.request.EmailVerifyRequest;
import com.poko.apps.user.application.dto.auth.request.ExistsEmailRequest;
import com.poko.apps.user.application.dto.auth.request.ExistsPhoneRequest;
import com.poko.apps.user.application.dto.auth.request.GenerateTokenRequest;
import com.poko.apps.user.application.dto.auth.request.LoginRequest;
import com.poko.apps.user.application.dto.auth.request.SignupRequest;
import com.poko.apps.user.application.dto.auth.response.EmailCodeResponse;
import com.poko.apps.user.application.dto.auth.response.GenerateTokenResponse;
import com.poko.apps.user.application.dto.auth.response.LoginResponse;
import com.poko.apps.user.application.dto.auth.response.SignupResponse;
import com.poko.apps.user.application.dto.auth.request.EmailCodeRequest;
import com.poko.apps.user.domain.entity.User;
import com.poko.apps.user.domain.enums.auth.AuthSuccessCode;
import com.poko.apps.user.domain.jwt.JwtProvider;
import com.poko.apps.user.domain.repository.auth.AuthRepository;
import com.poko.apps.user.domain.repository.auth.RefreshTokenRepository;
import java.time.Duration;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
  private final JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String email;


  private boolean existsUserByEmail(String email) {
    return authRepository.existsUserByEmail(email);
  }

  private boolean existsUserByPhone(String phone) {
    return authRepository.existsUserByPhone(phone);
  }

  private User findUserById(Long userId) {
    return authRepository.findById(userId)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND_BY_ID));
  }

  private User findUserByPhone(String phone) {
    return authRepository.findByPhone(phone)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND_BY_PHONE));
  }

  private User findUserByEmail(String email) {
    return authRepository.findByEmail(email)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND_BY_EMAIL));
  }

  private void passwordMatch(String password, User user) {
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new CustomException(INVALID_PASSWORD);
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
    if (accessToken.isBlank()) {
      throw new CustomException(INVALID_BEARER_TOKEN);
    }

    long remainingMillis = jwtProvider.getRemainingMillisByToken(accessToken);
    String jti = jwtProvider.getTokenId(accessToken);
    Long userId = jwtProvider.getUserIdByToken(accessToken);

    refreshTokenRepository.setTokenBlacklist(jti, remainingMillis);
    refreshTokenRepository.deleteRefreshToken(userId);
  }

  @Transactional
  @Override
  public GenerateTokenResponse generateToken(String accessToken, GenerateTokenRequest request) {
    Long userId = jwtProvider.getUserIdByToken(accessToken);

    User user = findUserById(userId);

    String newAccessToken = jwtProvider.createAccessToken(user.getId(), user.getUserRoleType());
    String newRefreshToken = jwtProvider.refreshAccessToken(user.getId());

    refreshTokenRepository.deleteRefreshToken(user.getId());
    refreshTokenRepository.saveRefreshToken(user.getId(), newRefreshToken, jwtProvider.getRefreshTokenExpire());

    return GenerateTokenResponse.of(newAccessToken, newRefreshToken);
  }

  @Override
  public EmailCodeResponse emailCode(EmailCodeRequest request) {
    Integer code = generateRandomNumber();

    // Redis 등에 저장 (TTL: 예 3분)
    refreshTokenRepository.setEmailCode(request.email(), code.toString(), Duration.ofMinutes(3));

    // 이메일 전송
    send(request.email(), code);

    return new EmailCodeResponse("인증 코드가 전송되었습니다.");
  }

  @Override
  public void verifyCode(EmailVerifyRequest request) {
    String savedCode = refreshTokenRepository.getEmailCode(request.email());
    String inputCode = request.verifyCode().toString();

    if (savedCode.isBlank() || inputCode.isBlank() || !savedCode.equals(inputCode)) {
      throw new CustomException(INVALID_INPUT);
    }

  }


  public void send(String to, int code) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setFrom(email);
    message.setSubject("[POKO] 이메일 인증 코드");
    message.setText("인증 코드: " + code + "\n3분 이내에 입력해 주세요.");
    mailSender.send(message);
  }

  private Integer generateRandomNumber() {
    Random random = new Random();
    StringBuilder randomNumber = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      randomNumber.append(random.nextInt(10));
    }
    return Integer.parseInt(randomNumber.toString());
  }



}
