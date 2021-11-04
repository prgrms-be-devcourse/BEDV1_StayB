package org.programmers.staybb.dto.user;

import lombok.Getter;

@Getter
public class UserIdResponse {

    private final Long id;

    public UserIdResponse(Long id) {
        this.id = id;
    }

}
