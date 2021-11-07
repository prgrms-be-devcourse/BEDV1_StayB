package org.programmers.staybb.dto.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.reservation.Reservation;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class FindReservationByHostResponse {

    private final Long id;
    private final Long userId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Guest guest;
    private final int totalPrice;
    private final String roomName;
    private final String userName;
    private final String userPhoneNumber;
    private final String message;
    private final LocalDateTime createdAt;

    public static FindReservationByHostResponse of(Reservation entity) {
        return FindReservationByHostResponse.builder()
            .id(entity.getId())
            .userId(entity.getUser().getId())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .guest(entity.getGuest())
            .totalPrice(entity.getTotalPrice())
            .roomName(entity.getRoom().getRoomName())
            .userName(entity.getUser().getName())
            .message(entity.getMessage())
            .userPhoneNumber(entity.getUser().getPhoneNumber())
            .createdAt(entity.getCreatedAt())
            .build();

    }

}
