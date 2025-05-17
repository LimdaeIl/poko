package com.poko.apps.user.application.service;

import com.poko.apps.user.application.dto.request.SignupRequest;
import com.poko.apps.user.application.dto.request.existsEmailRequest;
import com.poko.apps.user.application.dto.request.existsPhoneRequest;
import com.poko.apps.user.application.dto.response.SignupResponse;
import com.poko.apps.user.domain.enums.auth.AuthSuccessCode;

public interface AuthService {
  SignupResponse signup(SignupRequest request);

  AuthSuccessCode existsEmail(existsEmailRequest request);

  AuthSuccessCode existsPhone(existsPhoneRequest request);
}
