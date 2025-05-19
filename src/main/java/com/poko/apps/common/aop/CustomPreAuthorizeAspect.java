package com.poko.apps.common.aop;

import static com.poko.apps.common.exception.CommonErrorCode.BAD_REQUEST_CONTEXT_HOLDER;
import static com.poko.apps.common.exception.CommonErrorCode.INVALID_ROLE_VALUE;
import static com.poko.apps.common.exception.CommonErrorCode.NOT_FOUND_USER_ROLE_BY_HEADER;

import com.poko.apps.common.domain.enums.UserRoleType;
import com.poko.apps.common.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j(topic = "CustomPreAuthorizeAspect")
@Aspect
@Component
public class CustomPreAuthorizeAspect {

  private static final String USER_ROLE_HEADER = "X-User-Role";

  @Before("@annotation(customPreAuthorize)")
  public void customPreAuthorized(JoinPoint joinPoint, CustomPreAuthorize customPreAuthorize) {
    Set<UserRoleType> allowedRoles = Set.of(customPreAuthorize.userRoleType());
    UserRoleType currentUserRole = getCurrentUserRoleType();

    if (!allowedRoles.contains(currentUserRole)) {
      log.warn("접근 권한 없음: 현재 역할={}, 허용 역할={}", currentUserRole, allowedRoles);
      throw new CustomException(INVALID_ROLE_VALUE);
    }
  }

  private UserRoleType getCurrentUserRoleType() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    if (attributes == null) {
      log.warn(BAD_REQUEST_CONTEXT_HOLDER.getMessage());
      throw new CustomException(BAD_REQUEST_CONTEXT_HOLDER);
    }

    HttpServletRequest request = attributes.getRequest();
    String userRoleByHeader = request.getHeader(USER_ROLE_HEADER);

    if (userRoleByHeader == null || userRoleByHeader.isBlank()) {
      log.warn(NOT_FOUND_USER_ROLE_BY_HEADER.getMessage());
      throw new CustomException(NOT_FOUND_USER_ROLE_BY_HEADER);
    }

    try {
      return UserRoleType.valueOf(userRoleByHeader);
    } catch (IllegalArgumentException e) {
      log.warn("{} : {}", INVALID_ROLE_VALUE.getMessage(), userRoleByHeader);
      throw new CustomException(INVALID_ROLE_VALUE);
    }
  }
}
