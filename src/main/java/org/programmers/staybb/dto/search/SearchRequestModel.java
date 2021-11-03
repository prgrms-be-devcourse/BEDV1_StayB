package org.programmers.staybb.dto.search;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;

@Getter
@Setter
public class SearchRequestModel {
    private String location = null;
    private LocalDate startDate = null;
    private LocalDate endDate = null;

    private int adult = 0;
    private int teen = 0;
    private int kid = 0;

    private int bedNum = 0;
    private int bedroomNum = 0;
    private double bathroomNum = 0;
}
