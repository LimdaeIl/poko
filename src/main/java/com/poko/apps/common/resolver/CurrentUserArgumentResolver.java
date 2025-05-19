package com.poko.apps.common.resolver;

import static com.poko.apps.common.exception.CommonErrorCode.INVALID_INPUT;
import static com.poko.apps.common.exception.CommonErrorCode.UNAUTHORIZED;

import com.poko.apps.common.domain.enums.UserRoleType;
import com.poko.apps.common.domain.vo.CurrentUserInfo;
import com.poko.apps.common.exception.CustomException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String USER_ID_HEADER = "X-User-Id";
  private static final String USER_ROLE_HEADER = "X-User_Role";

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

    if (userId == null || userRole == null) {
      throw new CustomException(UNAUTHORIZED);
    }

    try {
      long id = Long.parseLong(userId);
      UserRoleType role = UserRoleType.valueOf(userRole);
      return CurrentUserInfo.of(id, role);
    } catch (NumberFormatException e) {
      throw new CustomException(INVALID_INPUT);
    } catch (IllegalArgumentException e) {
      throw new CustomException(UNAUTHORIZED);
    }
  }
}
