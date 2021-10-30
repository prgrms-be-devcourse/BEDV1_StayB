package org.programmers.staybb.service.room;

import javassist.NotFoundException;
import org.programmers.staybb.converter.RoomConverter;
import org.programmers.staybb.dto.room.RoomRequestDto;
import org.programmers.staybb.dto.room.RoomResponseDto;
import org.programmers.staybb.repository.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {

  private final RoomRepository roomRepository;
  private final RoomConverter roomConverter;

  @Autowired
  public RoomService(RoomRepository roomRepository, RoomConverter roomConverter) {
    this.roomRepository = roomRepository;
    this.roomConverter = roomConverter;
  }


  @Transactional
  public RoomResponseDto save(RoomRequestDto roomDto) {
    return roomConverter.converRoomResponseDto(
        roomRepository.save(roomConverter.convertRoom(roomDto))
    );
  }

  public void delete(int roomId) throws NotFoundException {
    if (roomRepository.findById(roomId).isEmpty()) {
      throw new NotFoundException("삭제하려는 숙소 정보가 없습니다.");
    }
    roomRepository.deleteById(roomId);
  }

}
