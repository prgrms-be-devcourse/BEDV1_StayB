package org.programmers.staybb.dto.Reservation;

import lombok.Getter;

@Getter
public class ReservationIdResponse {

    private final Long id;

    public ReservationIdResponse(Long id) {
        this.id = id;
    }

}