package org.programmers.staybb.dto.Reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.reservation.Reservation;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.User;

@Getter
@Builder
public class ReservationSaveRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "체크인 날짜를 입력해주세요.")
    @Future(message = "체크인 날짜를 다시 입력해주세요.")
    private final LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "체크아웃 날짜를 입력해주세요.")
    @Future(message = "체크아웃 날짜를 다시 입력해주세요.")
    private final LocalDate endDate;

    @Positive(message = "성인은 1명 이상이어야 합니다.")
    private final int adult;

    @PositiveOrZero(message = "다시 입력해 주세요")
    private final int teen;

    @PositiveOrZero(message = "다시 입력해 주세요")
    private final int child;

    @NotBlank
    private final String message;

    @NotNull
    private final Long userId;

    @NotNull
    private final Long roomId;

    private Guest getGuest() {
        return new Guest(adult, teen, child);
    }

    public Reservation toEntity(User user, Room room) {
        return Reservation.builder()
            .startDate(this.startDate)
            .endDate(this.endDate)
            .guest(getGuest())
            .message(this.message)
            .user(user)
            .room(room)
            .build();
    }

}
