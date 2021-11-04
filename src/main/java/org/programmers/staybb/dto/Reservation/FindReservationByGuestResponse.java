package org.programmers.staybb.dto.Reservation;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.reservation.Reservation;
import org.programmers.staybb.domain.room.Address;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class FindReservationByGuestResponse {

    private final Long id;
    private final Long roomId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Guest guest;
    private final int totalPrice;
    private final Address address;
    private final String roomName;
    private final String hostName;
    private final String hostPhoneNumber;

    public static FindReservationByGuestResponse of(Reservation entity) {
        return FindReservationByGuestResponse.builder()
            .id(entity.getId())
            .roomId(entity.getRoom().getId())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .guest(entity.getGuest())
            .totalPrice(entity.getTotalPrice())
            .address(entity.getRoom().getAddress())
            .roomName(entity.getRoom().getRoomName())
            .hostName(entity.getRoom().getHost().getUser().getName())
            .hostPhoneNumber(entity.getRoom().getHost().getUser().getPhoneNumber())
            .build();
    }

}
