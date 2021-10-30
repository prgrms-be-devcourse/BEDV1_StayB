package org.programmers.staybb.dto.room;

import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class RoomRequestDto {

  private int userId;
  private String roomName;
  private int maxGuest;
  private int price;
  private String description;

  private Option option;
  private Address address;

  @Autowired
  private RoomService roomService;

  @Builder
  public RoomRequestDto(int userId, String roomName, int maxGuest, int price, String description,
      Option option, Address address) {
    this.userId = userId;
    this.roomName = roomName;
    this.maxGuest = maxGuest;
    this.price = price;
    this.description = description;
    this.option = option;
    this.address = address;
  }


}
