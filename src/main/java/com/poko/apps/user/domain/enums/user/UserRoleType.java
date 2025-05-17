package com.poko.apps.user.domain.enums.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRoleType {
  ROLE_USER("회원"),
  ROLE_STORE("점주"),
  ROLE_DELIVERY("배송기사"),
  ROLE_ADMIN("관리자");

  private final String name;
}
