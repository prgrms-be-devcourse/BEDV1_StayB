package org.programmers.staybb.repository;

import static org.programmers.staybb.domain.room.QRoom.room;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.dto.search.SearchRequest;
import org.programmers.staybb.dto.search.SearchRequestModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CustomizedRoomRepositoryImpl implements CustomizedRoomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public CustomizedRoomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guest;
    private Option option;

    @Override
    public Page<Room> findByFiltersOrderByCreatedAt(final SearchRequest searchRequest, Pageable pageable) {

        List<Room> rooms = jpaQueryFactory.selectFrom(room)
            .where(room.address.address.contains(searchRequest.getLocation()),
                room.maxGuest.goe(searchRequest.getGuest().getTotalGuest()),
                room.option.bedNum.goe(searchRequest.getOption().getBedNum()),
                room.option.bedroomNum.goe(searchRequest.getOption().getBedroomNum()),
                room.option.bathroomNum.goe(searchRequest.getOption().getBathroomNum()))
            .orderBy(room.createdAt.desc())
            .fetch();

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), rooms.size());
        final Page<Room> page = new PageImpl<>(rooms.subList(start, end), pageable, rooms.size());

        return page;
    }


//    @Override
//    public Optional<Room> findByRegion(final String region) {
//        return Optional.of(jpaQueryFactory.selectFrom(room)
//            .where(room.address.region.eq(region))
//            .fetchOne());
//    }
}
