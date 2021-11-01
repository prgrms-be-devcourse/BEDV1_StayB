package org.programmers.staybb.dto.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;


@Getter
public class SearchOneResponse {

    private Long id;
    private String roomName;
    private int maxGuest;
    private String description;

    private Option option;
    private String roughAddress;

    @Builder
    public SearchOneResponse(Room entity) {
        this.id = entity.getId();
        this.roomName = entity.getRoomName();
        this.maxGuest = entity.getMaxGuest();
        this.description = entity.getDescription();
        this.option = entity.getOption();
        this.roughAddress = entity.getAddress().getAddress();
    }
}
