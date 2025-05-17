package com.poko.apps.user.infrastructure.persistence.jpa;

import com.poko.apps.user.domain.entity.User;
import com.poko.apps.user.domain.repository.user.AuthRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthJpaRepository extends JpaRepository<User, Long>, AuthRepository {
}
