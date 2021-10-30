package org.programmers.staybb.converter;

import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.dto.room.RoomRequestDto;
import org.programmers.staybb.dto.room.RoomResponseDto;
import org.programmers.staybb.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomConverter {

  @Autowired
  private RoomService roomService;

  public Room convertRoom(RoomRequestDto roomRequestDto) {
    Room room = Room.builder().build();

    try {
      //Host host = userService.find(this.getUserId());
      //Host host = Host.builder().user().build();
      Host host = Host.builder().build();
      room.addRoom(host);
    } catch (Exception e) {

    }

    return room.builder()
        .roomName(roomRequestDto.getRoomName())
        .maxGuest(roomRequestDto.getMaxGuest())
        .price(roomRequestDto.getPrice())
        .description(roomRequestDto.getDescription())
        .address(roomRequestDto.getAddress())
        .option(roomRequestDto.getOption())
        .build();
  }

  public RoomResponseDto converRoomResponseDto(Room room) {
    return RoomResponseDto.builder()
        .roomName(room.getRoomName())
        .address(room.getAddress())
        .maxGuest(room.getMaxGuest())
        .option(room.getOption())
        .description(room.getDescription())
        .price(room.getPrice())
        .build();
  }
}
