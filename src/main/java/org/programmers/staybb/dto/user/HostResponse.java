package org.programmers.staybb.dto.user;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.user.Host;

@Getter
@Builder
public class HostResponse {

    private String name;

    private boolean isSuperhost;

    private List<Long> roomIds;

    public static HostResponse of(Host entity, String userName, List<Long> roomIds) {
        return HostResponse.builder()
            .name(userName)
            .isSuperhost(entity.is_superHost())
            .roomIds(roomIds)
            .build();
    }
}
