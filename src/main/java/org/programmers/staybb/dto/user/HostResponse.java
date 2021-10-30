package org.programmers.staybb.dto.user;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.user.Host;

@Getter
@Builder
public class HostResponse {

    private final String name;

    private final boolean is_superhost;

    private final List<Long> roomsId;

    public HostResponse(Host entity, String userName, List<Long> roomsId) {
        this.name = userName;
        this.is_superhost = entity.is_superHost();
        this.roomsId = roomsId;
    }
}
