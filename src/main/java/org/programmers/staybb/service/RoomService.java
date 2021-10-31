package org.programmers.staybb.service;

import javassist.NotFoundException;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.dto.room.RoomRequestDto;
import org.programmers.staybb.repository.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public Long save(RoomRequestDto roomDto) {
        return roomRepository.save(roomDto.toEntity()).getId();
    }

    @Transactional
    public long delete(int roomId) throws NotFoundException {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new NotFoundException("해당 숙소 정보가 없습니다."));
        roomRepository.deleteById(roomId);
        return room.getId();
    }

}
