package org.programmers.staybb.dto.Reservation;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.reservation.Guest;

@Getter
public class GuestRequest {

    @Positive(message = "성인은 1명 이상이어야 합니다.")
    private final int adult;
    @PositiveOrZero(message = "다시 입력해 주세요")
    private final int teen;
    @PositiveOrZero(message = "다시 입력해 주세요")
    private final int child;

    @Builder
    public GuestRequest(int adult, int teen, int child) {
        this.adult = adult;
        this.teen = teen;
        this.child = child;
    }

    public Guest toEntity() {
        return new Guest(this.adult, this.teen, this.child);
    }
    
}
