package org.programmers.staybb.dto.room;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;

@Getter
public class RoomSummaryResponse {

    private String address;
    private Option option;
    private LocalDateTime updatedAt;

    @Builder
    private RoomSummaryResponse(String address, Option option, LocalDateTime updatedAt) {
        this.address = address;
        this.option = option;
        this.updatedAt = updatedAt;
    }

    public static RoomSummaryResponse of(Room entity) {
        return RoomSummaryResponse.builder()
            .address(entity.getAddress().getAddress())
            .option(entity.getOption())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

}

