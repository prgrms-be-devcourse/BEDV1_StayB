package org.programmers.staybb.repository;

import org.programmers.staybb.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
