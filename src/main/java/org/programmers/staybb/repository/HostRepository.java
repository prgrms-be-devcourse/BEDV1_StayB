package org.programmers.staybb.repository;

import java.util.Optional;
import org.programmers.staybb.domain.user.Host;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long> {

    Optional<Host> findByUserId(Long userId);

}
