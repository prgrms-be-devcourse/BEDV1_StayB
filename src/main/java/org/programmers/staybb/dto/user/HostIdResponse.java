package org.programmers.staybb.dto.user;

import lombok.Getter;

@Getter
public class HostIdResponse {

    private final Long id;

    public HostIdResponse(Long id) {
        this.id = id;
    }

}