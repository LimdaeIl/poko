package com.poko.apps.user.domain.repository.user;

import com.poko.apps.user.domain.entity.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findByEmail(String email);
  Optional<User> findByPhone(String phone);
  User save(User user);

  boolean existsUserByEmail(String email);
  boolean existsUserByPhone(String phone);

  Optional<User> findById(Long userId);
}
