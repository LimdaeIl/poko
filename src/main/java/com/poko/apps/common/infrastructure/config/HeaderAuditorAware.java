package com.poko.apps.common.infrastructure.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HeaderAuditorAware implements AuditorAware<Long> {

  @NotNull
  @Override
  public Optional<Long> getCurrentAuditor() {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (requestAttributes == null) {
      return Optional.of(0L); // 0L: SYSTEM
    }

    HttpServletRequest request = requestAttributes.getRequest();
    String userIdHeader = request.getHeader("X-User-Id");

    return Optional.ofNullable(parseLongOrNull(userIdHeader)).or(() -> Optional.of(0L));
  }

  private Long parseLongOrNull(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }
    return value.chars().allMatch(Character::isDigit) ? Long.valueOf(value) : null;
  }
}