package org.programmers.staybb.dto.Reservation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.reservation.Reservation;
import org.programmers.staybb.domain.room.CheckDate;

@Getter
@Builder(access = AccessLevel.PRIVATE)
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
