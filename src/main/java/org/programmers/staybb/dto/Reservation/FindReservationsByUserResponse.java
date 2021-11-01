package org.programmers.staybb.dto.Reservation;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.reservation.Reservation;

@Getter
@Builder
public class FindReservationsByUserResponse {

    private final Long id;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String roomRegion;
    private final String roomName;

    public static FindReservationsByUserResponse of(Reservation entity, String roomRegion,
        String roomName) {
        return FindReservationsByUserResponse.builder()
            .id(entity.getId())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .roomRegion(roomRegion)
            .roomName(roomName)
            .build();
    }


}
