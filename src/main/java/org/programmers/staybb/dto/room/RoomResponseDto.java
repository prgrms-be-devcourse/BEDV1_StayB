package org.programmers.staybb.dto.room;

import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;

@Getter
public class RoomResponseDto {

  private int roomId;
  private String roomName;
  private int maxGuest;
  private int price;
  private String description;

  private Option option;
  private Address address;

  @Builder
  public RoomResponseDto(int roomId, String roomName, int maxGuest, int price,
      String description, Option option, Address address) {
    this.roomId = roomId;
    this.roomName = roomName;
    this.maxGuest = maxGuest;
    this.price = price;
    this.description = description;
    this.option = option;
    this.address = address;
  }

}
