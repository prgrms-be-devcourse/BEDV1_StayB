package org.programmers.staybb.dto.search;

import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;


@Getter
public class SearchAllResponse {

    private Long id;
    private String roomName;
    private int maxGuest;

    private Option option;
    private String region;

    @Builder
    public SearchAllResponse(Room entity) {
        this.id = entity.getId();
        this.roomName = entity.getRoomName();
        this.maxGuest = entity.getMaxGuest();
        this.option = entity.getOption();
        this.region = entity.getAddress().getRegion();
    }
}
