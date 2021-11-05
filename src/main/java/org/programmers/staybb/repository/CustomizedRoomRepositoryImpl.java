package org.programmers.staybb.repository;

import static org.programmers.staybb.domain.reservation.QReservation.reservation;
import static org.programmers.staybb.domain.room.QRoom.room;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.dto.search.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CustomizedRoomRepositoryImpl implements CustomizedRoomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomizedRoomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Room> findByFiltersOrderByCreatedAt(final SearchRequest searchRequest,
        Pageable pageable) {
        List<Room> rooms = jpaQueryFactory.selectFrom(room)
            .where(
                dateBtw(searchRequest.getStartDate(), searchRequest.getEndDate()),
                addressCtn(searchRequest.getLocation()),
                maxGuestGoe(searchRequest.getGuest().getTotalGuest()),
                bedNumGoe(searchRequest.getOption().getBedNum()),
                bedroomNumGoe(searchRequest.getOption().getBedNum()),
                bathroomNumGoe(searchRequest.getOption().getBathroomNum())
            )
            .orderBy(room.createdAt.desc())
            .fetch();
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), rooms.size());
        final Page<Room> page = new PageImpl<>(rooms.subList(start, end), pageable, rooms.size());
        return page;
    }

    private Predicate dateBtw(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        return room.id.notIn(
            JPAExpressions.select(reservation.room.id)
                .from(reservation)
                .where(
                    (reservation.startDate.goe(startDate).and(reservation.startDate.lt(endDate)))
                        .or(reservation.startDate.loe(startDate)
                            .and(reservation.endDate.gt(startDate)))
                )
        );
    }

    private Predicate addressCtn(final String location) {
        if (location == null) {
            return null;
        }
        return room.address.address.contains(location);
    }

    private Predicate maxGuestGoe(final int totalGuest) {
        if (totalGuest == 0) {
            return null;
        }
        return room.maxGuest.goe(totalGuest);
    }

    private Predicate bedNumGoe(final int bedNum) {
        if (bedNum == 0) {
            return null;
        }
        return room.maxGuest.goe(bedNum);
    }

    private Predicate bedroomNumGoe(final int bathroomNum) {
        if (bathroomNum == 0) {
            return null;
        }
        return room.maxGuest.goe(bathroomNum);
    }

    private Predicate bathroomNumGoe(final double bathroomNum) {
        if (bathroomNum == 0) {
            return null;
        }
        return room.maxGuest.goe(bathroomNum);
    }
}