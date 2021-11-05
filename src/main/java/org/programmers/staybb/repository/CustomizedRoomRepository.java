package org.programmers.staybb.repository;

import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.dto.search.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomizedRoomRepository {

    Page<Room> findByFiltersOrderByCreatedAt(final SearchRequest searchRequest, Pageable pageable);
}
