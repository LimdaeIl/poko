package com.poko.apps.user.presentation;

import static com.poko.apps.user.domain.enums.auth.AuthSuccessCode.USER_LOGIN_SUCCESS;
import static com.poko.apps.user.domain.enums.auth.AuthSuccessCode.USER_SIGNUP_SUCCESS;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.poko.apps.common.util.response.ApiResponse;
import com.poko.apps.user.application.dto.auth.request.ExistsEmailRequest;
import com.poko.apps.user.application.dto.auth.request.ExistsPhoneRequest;
import com.poko.apps.user.application.dto.auth.request.LoginRequest;
import com.poko.apps.user.application.dto.auth.request.SignupRequest;
import com.poko.apps.user.application.dto.auth.response.ExistsEmailResponse;
import com.poko.apps.user.application.dto.auth.response.ExistsPhoneResponse;
import com.poko.apps.user.application.dto.auth.response.LoginResponse;
import com.poko.apps.user.application.dto.auth.response.SignupResponse;
import com.poko.apps.user.application.service.AuthService;
import com.poko.apps.user.domain.enums.auth.AuthSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest request) {

    return ResponseEntity
        .status(CREATED)
        .body(
            new ApiResponse<>(
                USER_SIGNUP_SUCCESS.getCode(),
                USER_SIGNUP_SUCCESS.getMessage(),
                authService.signup(request)
            )
        );
  }

  @PostMapping("/email-exists")
  public ResponseEntity<ApiResponse<ExistsEmailResponse>> existsEmail(@Valid @RequestBody ExistsEmailRequest request) {
    AuthSuccessCode authSuccessCode = authService.existsEmail(request);

    return ResponseEntity
        .status(OK)
        .body(
            new ApiResponse<>(
                authSuccessCode.getCode(),
                authSuccessCode.getMessage(),
                null
            )
        );
  }

  @PostMapping("/phone-exists")
  public ResponseEntity<ApiResponse<ExistsPhoneResponse>> existsPhone(@Valid @RequestBody ExistsPhoneRequest request) {
    AuthSuccessCode authSuccessCode = authService.existsPhone(request);

    return ResponseEntity
        .status(OK)
        .body(
            new ApiResponse<>(
                authSuccessCode.getCode(),
                authSuccessCode.getMessage(),
                null
            )
        );
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity
        .status(OK)
        .body(
            new ApiResponse<>(
                USER_LOGIN_SUCCESS.getCode(),
                USER_LOGIN_SUCCESS.getMessage(),
                authService.login(request)
            )
        );
  }


}
