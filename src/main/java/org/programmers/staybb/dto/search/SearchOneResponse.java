package org.programmers.staybb.dto.search;

import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;

@Getter
public class SearchOneResponse {

    private final Long id;
    private final String roomName;
    private final int maxGuest;
    private final String description;
    private final Option option;
    private final String roughAddress;

    @Builder
    public SearchOneResponse(Room entity) {
        this.id = entity.getId();
        this.roomName = entity.getRoomName();
        this.maxGuest = entity.getMaxGuest();
        this.description = entity.getDescription();
        this.option = entity.getOption();
        this.roughAddress = entity.getAddress().getAddress();
    }
}
