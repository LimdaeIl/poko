package com.poko.apps.user.infrastructure.persistence.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import java.time.LocalDateTime;
import java.util.Collection;

public final class UserPredicate {

  private UserPredicate() {
    throw new UnsupportedOperationException("유틸리티 클래스로 인스턴스화할 수 없습니다.");
  }

  public static BooleanExpression like(StringPath path, String keyword) {
    return (keyword == null || keyword.isBlank()) ? null : path.containsIgnoreCase(keyword.trim());
  }

  public static BooleanExpression eq(StringPath path, String keyword) {
    return (keyword == null || keyword.isBlank()) ? null : path.equalsIgnoreCase(keyword.trim());
  }

  public static <T> BooleanExpression eq(SimpleExpression<T> path, T value) {
    return value == null ? null : path.eq(value);
  }

  public static BooleanExpression eq(BooleanPath path, Boolean value) {
    return value == null ? null : path.eq(value);
  }

  public static <T> BooleanExpression in(SimpleExpression<T> path, Collection<T> values) {
    return (values == null || values.isEmpty()) ? null : path.in(values);
  }

  public static BooleanExpression between(DateTimePath<LocalDateTime> path, LocalDateTime from, LocalDateTime to) {
    if (from != null && to != null) {
      return path.between(from, to);
    }
    if (from != null) {
      return path.goe(from);
    }
    if (to != null) {
      return path.loe(to);
    }
    return null;
  }

}
