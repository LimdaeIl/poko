package com.poko.apps.user.application.service;

import com.poko.apps.user.application.dto.request.SignupRequest;
import com.poko.apps.user.application.dto.request.ExistsEmailRequest;
import com.poko.apps.user.application.dto.request.ExistsPhoneRequest;
import com.poko.apps.user.application.dto.response.SignupResponse;
import com.poko.apps.user.domain.enums.auth.AuthSuccessCode;

public interface AuthService {
  SignupResponse signup(SignupRequest request);

  AuthSuccessCode existsEmail(ExistsEmailRequest request);

  AuthSuccessCode existsPhone(ExistsPhoneRequest request);
}
