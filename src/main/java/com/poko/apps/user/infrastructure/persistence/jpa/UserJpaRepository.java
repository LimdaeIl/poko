package com.poko.apps.user.infrastructure.persistence.jpa;

import com.poko.apps.user.domain.entity.User;
import com.poko.apps.user.domain.repository.user.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

}
