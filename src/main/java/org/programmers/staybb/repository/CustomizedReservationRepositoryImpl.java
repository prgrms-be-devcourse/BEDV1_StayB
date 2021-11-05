package org.programmers.staybb.repository;

import static org.programmers.staybb.domain.reservation.QReservation.reservation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import org.programmers.staybb.domain.reservation.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CustomizedReservationRepositoryImpl implements CustomizedReservationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomizedReservationRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Reservation> findAllByUserIdOrderByCreatedAt(Long userId, Pageable pageable) {
        List<Reservation> reservations = jpaQueryFactory.selectFrom(reservation)
            .where(
                reservation.user.id.eq(userId),
                reservation.startDate.goe(LocalDate.now())
            )
            .orderBy(reservation.createdAt.asc())
            .fetch();

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), reservations.size());
        final Page<Reservation> page = new PageImpl<>(reservations.subList(start, end), pageable,
            reservations.size());

        return page;
    }
}
