package org.programmers.staybb.dto.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;

@Getter
@Setter
public class SearchRequest {

    private String location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "현재보다 이전인 날짜를 전달받았습니다.")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "현재보다 이전인 날짜를 전달받았습니다.")
    private LocalDate endDate;
    private Guest guest;
    private Option option;

    public SearchRequest(SearchRequestModel searchRequestModel) {
        this.location = searchRequestModel.getLocation();
        this.startDate = LocalDate.parse(searchRequestModel.getStartDate());
        this.endDate = LocalDate.parse(searchRequestModel.getEndDate());
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