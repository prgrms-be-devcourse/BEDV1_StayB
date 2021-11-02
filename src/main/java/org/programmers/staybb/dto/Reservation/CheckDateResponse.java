package org.programmers.staybb.dto.Reservation;

import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.reservation.Reservation;
import org.programmers.staybb.domain.room.CheckDate;

@Getter
@Builder
public class CheckDateResponse {

    private final Long reservationId;
    private final CheckDate checkDate;

    public static CheckDateResponse of(Reservation reservation) {
        return CheckDateResponse.builder()
            .reservationId(reservation.getId())
            .checkDate(new CheckDate(reservation.getStartDate(), reservation.getEndDate()))
            .build();
    }

}
