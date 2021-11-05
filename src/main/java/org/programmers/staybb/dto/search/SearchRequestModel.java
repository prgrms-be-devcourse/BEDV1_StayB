package org.programmers.staybb.dto.search;

import java.time.LocalDate;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class SearchRequestModel {

    private String location = null;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate = null;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate = null;
    @Min(value = 0, message = "성인으로 검색할 수 있는 값은 0이상입니다.")
    @Max(value = 16, message = "성인으로 검색할 수 있는 값은 16이하입니다.")
    private int adult = 0;
    @Min(value = 0, message = "어린이로 검색할 수 있는 값은 0이상입니다.")
    @Max(value = 5, message = "어린이로 검색할 수 있는 값은 5이하입니다.")
    private int teen = 0;
    @Min(value = 0, message = "유아로 검색할 수 있는 값은 0이상입니다.")
    @Max(value = 5, message = "유아로 검색할 수 있는 값은 5이하입니다.")
    private int kid = 0;
    @Min(value = 0, message = "침대 수으로 검색할 수 있는 값은 0이상입니다.")
    @Max(value = 16, message = "침대 수으로 검색할 수 있는 값은 16이하입니다.")
    private int bedNum = 0;
    @Min(value = 0, message = "침실 수로 검색할 수 있는 값은 0이상입니다.")
    @Max(value = 16, message = "침실 수로 검색할 수 있는 값은 16이하입니다.")
    private int bedroomNum = 0;
    @Min(value = 0, message = "욕실 수으로 검색할 수 있는 값은 0이상입니다.")
    @Max(value = 16, message = "욕실 수으로 검색할 수 있는 값은 16이하입니다.")
    private double bathroomNum = 0;
}
