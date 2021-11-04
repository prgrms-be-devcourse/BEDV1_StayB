package org.programmers.staybb.dto.user;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.user.Host;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class HostFindResponse {

    private final String name;

    private final boolean isSuperhost;

    private final List<Long> roomIds;

    public static HostFindResponse of(Host entity, String userName, List<Long> roomIds) {
        return HostFindResponse.builder()
            .name(userName)
            .isSuperhost(entity.is_superHost())
            .roomIds(roomIds)
            .build();
    }

}
