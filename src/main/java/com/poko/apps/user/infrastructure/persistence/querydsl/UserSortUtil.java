package com.poko.apps.user.infrastructure.persistence.querydsl;

import com.poko.apps.user.domain.entity.User;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;

public class UserSortUtil {

  private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
      "id", "email", "name", "phone", "profileImage", "userRoleType",
      "createdAt", "createdBy", "modifiedAt", "modifiedBy", "deletedAt", "deletedBy"
  );

  private UserSortUtil() {
    throw new UnsupportedOperationException("유틸리티 클래스로 인스턴스화할 수 없습니다.");
  }

  public static List<OrderSpecifier<?>> toOrderSpecifiers(Sort sort) {
    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
    PathBuilder<User> entityPath = new PathBuilder<>(User.class, "user");

    for (Sort.Order order : sort) {
      String property = order.getProperty().trim();
      if (!ALLOWED_SORT_FIELDS.contains(property)) {
        continue;
      }

      Order direction = order.isAscending() ? Order.ASC : Order.DESC;

      orderSpecifiers.add(new OrderSpecifier<>(
          direction,
          entityPath.getComparable(property, Comparable.class)
      ));
    }

    return orderSpecifiers;
  }


}
