package org.programmers.staybb.repository;

import org.programmers.staybb.domain.room.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long>,
    CustomizedRoomRepository {

    Page<Room> findAllByHostId(Long hostId, Pageable pageable);

}
