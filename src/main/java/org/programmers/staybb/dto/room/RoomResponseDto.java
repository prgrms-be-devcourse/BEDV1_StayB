package org.programmers.staybb.dto.room;

import lombok.Builder;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;

@Builder
public class RoomResponseDto {

    private int userId;
    private String roomName;
    private int maxGuest;
    private int price;
    private String description;

    private Option option;
    private Address address;

    public static RoomResponseDto toDto(Room entity) {
        return RoomResponseDto.builder()
            .roomName(entity.getRoomName())
            .maxGuest(entity.getMaxGuest())
            .price(entity.getPrice())
            .description(entity.getDescription())
            .option(entity.getOption())
            .address(entity.getAddress())
            .build();
    }

}
