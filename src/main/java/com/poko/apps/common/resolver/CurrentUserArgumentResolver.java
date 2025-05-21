package com.poko.apps.common.resolver;

import static com.poko.apps.common.exception.CommonErrorCode.INVALID_ROLE_VALUE;
import static com.poko.apps.common.exception.CommonErrorCode.INVALID_USER_ID_VALUE;
import static com.poko.apps.common.exception.CommonErrorCode.UNAUTHORIZED;

import com.poko.apps.common.domain.enums.UserRoleType;
import com.poko.apps.common.domain.vo.CurrentUserInfo;
import com.poko.apps.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String USER_ID_HEADER = "X-User-Id";
  private static final String USER_ROLE_HEADER = "X-User-Role";

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(CurrentUser.class)
        && parameter.getParameterType().equals(CurrentUserInfo.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

    String userId = webRequest.getHeader(USER_ID_HEADER);
    String userRole = webRequest.getHeader(USER_ROLE_HEADER);
    log.info("userId: {}, userRole: {}", userId, userRole);

    if (userId == null || userRole == null) {
      throw new CustomException(UNAUTHORIZED);
    }

    try {
      long id = Long.parseLong(userId);

      if (id <= 0) {
        log.warn("유효하지 않은 userId 값: {}", userId);
        throw new CustomException(INVALID_USER_ID_VALUE);
      }

      UserRoleType role = UserRoleType.valueOf(userRole);
      return CurrentUserInfo.of(id, role);
    } catch (NumberFormatException e) {
      log.warn("유효하지 않은 userId 형식: {}", e.toString());
      throw new CustomException(INVALID_USER_ID_VALUE);
    } catch (IllegalArgumentException e) {
      log.warn("유효하지 않은 userRole 값: {}", userRole);
      throw new CustomException(INVALID_ROLE_VALUE);
    }
  }
}
