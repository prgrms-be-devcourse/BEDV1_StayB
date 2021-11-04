package org.programmers.staybb.dto.room;

import lombok.Getter;

@Getter
public class RoomIdResponse {

    private final Long id;

    public RoomIdResponse(Long id) {
        this.id = id;
    }

}
