package org.programmers.staybb.repository;

import java.util.List;
import org.programmers.staybb.domain.reservation.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>,
    CustomizedReservationRepository {

    Page<Reservation> findAllByUserIdOrderByCreatedAt(Long userId, Pageable pageable);

    List<Reservation> findAllByRoomId(Long roomId);

}
