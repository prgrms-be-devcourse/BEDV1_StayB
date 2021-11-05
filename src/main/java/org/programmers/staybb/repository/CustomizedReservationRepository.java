package org.programmers.staybb.repository;

import org.programmers.staybb.domain.reservation.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomizedReservationRepository {

    Page<Reservation> findAllByUserIdOrderByCreatedAt(Long userId,
        Pageable pageable);
}
