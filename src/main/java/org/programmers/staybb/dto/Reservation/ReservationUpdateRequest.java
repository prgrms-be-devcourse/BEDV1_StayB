package org.programmers.staybb.dto.Reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.programmers.staybb.domain.reservation.Guest;

@Builder
public class ReservationUpdateRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @NotNull(message = "체크인 날짜를 입력해주세요.")
    @Past(message = "체크인 날짜를 다시 입력해주세요.")
    private final LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @NotNull(message = "체크아웃 날짜를 입력해주세요.")
    @Past(message = "체크아웃 날짜를 다시 입력해주세요.")
    private final LocalDate endDate;

    @Positive(message = "성인은 1명 이상이어야 합니다.")
    private final int adult;

    @PositiveOrZero(message = "다시 입력해 주세요")
    private final int teen;

    @PositiveOrZero(message = "다시 입력해 주세요")
    private final int child;

    public Guest getGuest() {
        return new Guest(adult, teen, child);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
