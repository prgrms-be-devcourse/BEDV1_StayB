package org.programmers.staybb.dto.room;

import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;

@Getter
public class RoomRequest {
    private Long hostId;
    private String roomName;
    private int maxGuest;
    private int price;
    private String description;

    private Option option;
    private Address address;

    @Builder
    public RoomRequest(Long hostId, String roomName, int maxGuest, int price, String description,
        Option option, Address address) {
        this.hostId = hostId;
        this.roomName = roomName;
        this.maxGuest = maxGuest;
        this.price = price;
        this.description = description;
        this.option = option;
        this.address = address;
    }

    public Room toEntity(Host host) {
        return Room.builder()
            .host(host)
            .roomName(this.getRoomName())
            .maxGuest(this.getMaxGuest())
            .price(this.getPrice())
            .description(this.getDescription())
            .option(this.getOption())
            .address(this.getAddress())
            .build();
    }
}