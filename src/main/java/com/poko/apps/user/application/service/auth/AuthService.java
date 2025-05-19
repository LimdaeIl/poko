package com.poko.apps.user.application.service.auth;

import com.poko.apps.user.application.dto.auth.request.ExistsEmailRequest;
import com.poko.apps.user.application.dto.auth.request.ExistsPhoneRequest;
import com.poko.apps.user.application.dto.auth.request.GenerateTokenRequest;
import com.poko.apps.user.application.dto.auth.request.LoginRequest;
import com.poko.apps.user.application.dto.auth.request.SignupRequest;
import com.poko.apps.user.application.dto.auth.response.GenerateTokenResponse;
import com.poko.apps.user.application.dto.auth.response.LoginResponse;
import com.poko.apps.user.application.dto.auth.response.SignupResponse;
import com.poko.apps.user.domain.enums.auth.AuthSuccessCode;
import jakarta.validation.Valid;

public interface AuthService {
  SignupResponse signup(SignupRequest request);

  AuthSuccessCode existsEmail(ExistsEmailRequest request);

  AuthSuccessCode existsPhone(ExistsPhoneRequest request);

  LoginResponse login(@Valid LoginRequest request);

  void logout(String accessToken);

  GenerateTokenResponse generateToken(String accessToken, GenerateTokenRequest request);
}
