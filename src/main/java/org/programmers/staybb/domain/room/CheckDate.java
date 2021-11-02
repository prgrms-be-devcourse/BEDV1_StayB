package org.programmers.staybb.domain.room;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CheckDate {

    private final LocalDate startDate;
    private final LocalDate endDate;

    public CheckDate(LocalDate checkIn, LocalDate checkOut) {
        this.startDate = checkIn;
        this.endDate = checkOut;
    }

}
