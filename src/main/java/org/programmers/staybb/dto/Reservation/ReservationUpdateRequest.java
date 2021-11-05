package org.programmers.staybb.dto.Reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReservationUpdateRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "체크인 날짜를 입력해주세요.")
    @Future(message = "체크인 날짜를 다시 입력해주세요.")
    private final LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "체크아웃 날짜를 입력해주세요.")
    @Future(message = "체크아웃 날짜를 다시 입력해주세요.")
    private final LocalDate endDate;

    @NotNull(message = "숙박 인원을 입력해주세요.")
    private final GuestRequest guestRequest;

}
