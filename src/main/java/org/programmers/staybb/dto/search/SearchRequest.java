package org.programmers.staybb.dto.search;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;

@Getter
public class SearchRequest {
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guest;
    private Option option;

    @Builder
    public SearchRequest(String location, LocalDate startDate, LocalDate endDate,
        Guest guest, Option option) {
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guest = guest;
        this.option = option;
    }
}
