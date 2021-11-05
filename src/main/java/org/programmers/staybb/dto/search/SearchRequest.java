package org.programmers.staybb.dto.search;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.room.Option;

@Getter
@Setter
public class SearchRequest {

    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guest;
    private Option option;

    public SearchRequest(SearchRequestModel searchRequestModel) {
        this.location = searchRequestModel.getLocation();
        this.startDate = searchRequestModel.getStartDate();
        this.endDate = searchRequestModel.getEndDate();
        this.guest = new Guest(
            searchRequestModel.getAdult(),
            searchRequestModel.getKid(),
            searchRequestModel.getTeen());
        this.option = Option.builder()
            .bedNum(searchRequestModel.getBedNum())
            .bedroomNum(searchRequestModel.getBedroomNum())
            .bathroomNum(searchRequestModel.getBathroomNum())
            .build();
    }
}
