package com.poko.apps.user.infrastructure.persistence.querydsl;

import com.poko.apps.user.application.dto.auth.request.UserSearchCondition;
import com.poko.apps.user.application.dto.auth.response.GetUsersResponse;
import com.poko.apps.user.domain.entity.QUser;
import com.poko.apps.user.domain.repository.user.UserQueryRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserQueryRepositoryImpl implements UserQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<GetUsersResponse> getUsersByCondition(UserSearchCondition condition, Pageable pageable) {
    QUser user = QUser.user;

    BooleanBuilder builder = new BooleanBuilder()
        .and(UserPredicate.eq(user.id, condition.id()))
        .and(UserPredicate.like(user.email, condition.email()))
        .and(UserPredicate.like(user.name, condition.name()))
        .and(UserPredicate.eq(user.userRoleType, condition.userRoleType()))
        .and(UserPredicate.eq(user.phone, condition.phone()))
        .and(UserPredicate.eq(user.createdBy, condition.createdBy()))
        .and(UserPredicate.eq(user.modifiedBy, condition.modifiedBy()))
        .and(UserPredicate.eq(user.deletedBy, condition.deletedBy()))
        .and(UserPredicate.between(user.createdAt, condition.createdFrom(), condition.createdTo()))
        .and(UserPredicate.between(user.modifiedAt, condition.modifiedFrom(), condition.modifiedTo()))
        .and(UserPredicate.between(user.deletedAt, condition.deletedFrom(), condition.deletedTo()));

    List<GetUsersResponse> contents = queryFactory
        .select(Projections.constructor(
            GetUsersResponse.class,
            user.id,
            user.email,
            user.name,
            user.phone,
            user.profileImage,
            user.userRoleType,
            user.createdBy,
            user.createdAt,
            user.modifiedBy,
            user.modifiedAt,
            user.deletedBy,
            user.deletedAt
        ))
        .from(user)
        .where(builder)
        .orderBy(UserSortUtil.toOrderSpecifiers(pageable.getSort()).toArray(new OrderSpecifier[0]))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = queryFactory
        .select(user.count())
        .from(user)
        .where(builder)
        .fetchOne();

    return new PageImpl<>(contents, pageable, count != null ? count : 0);
  }
}
