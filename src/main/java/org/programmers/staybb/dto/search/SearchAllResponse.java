package org.programmers.staybb.dto.search;

import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;

@Getter
public class SearchAllResponse {

    private final Long id;
    private final String roomName;
    private final int maxGuest;
    private final Option option;
    private final String region;

    @Builder
    public SearchAllResponse(Room entity) {
        this.id = entity.getId();
        this.roomName = entity.getRoomName();
        this.maxGuest = entity.getMaxGuest();
        this.option = entity.getOption();
        this.region = entity.getAddress().getRegion();
    }
}
