package org.programmers.staybb.dto.room;

import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;

@Getter
public class RoomDetailResponse {

    private String roomName;
    private int maxGuest;
    private int price;
    private String description;

    private Option option;
    private Address address;

    @Builder
    private RoomDetailResponse(String roomName, int maxGuest, int price,
        String description, Option option, Address address) {
        this.roomName = roomName;
        this.maxGuest = maxGuest;
        this.price = price;
        this.description = description;
        this.option = option;
        this.address = address;
    }

    public static RoomDetailResponse of(Room entity) {
        return RoomDetailResponse.builder()
            .roomName(entity.getRoomName())
            .maxGuest(entity.getMaxGuest())
            .price(entity.getPrice())
            .description(entity.getDescription())
            .option(entity.getOption())
            .address(entity.getAddress())
            .build();
    }

}
