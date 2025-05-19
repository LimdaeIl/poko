package com.poko.apps.common.aop;

import static com.poko.apps.common.domain.enums.UserRoleType.*;

import com.poko.apps.common.domain.enums.UserRoleType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomPreAuthorize {
  UserRoleType[] userRoleType() default {
      ROLE_USER,
      ROLE_ADMIN,
      ROLE_DELIVERY,
      ROLE_STORE
  };
}
