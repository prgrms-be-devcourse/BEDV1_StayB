package org.programmers.staybb.dto.room;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.dto.room.valid.AddressValid;
import org.programmers.staybb.dto.room.valid.OptionValid;

@Getter
public class RoomRequest {

    @Positive(message = "host id는 1 이상이어야 합니다.")
    private final Long hostId;

    @NotBlank(message = "숙소 이름은 1글자 이상이어야 하고 공백으로만 입력할 수 없습니다.")
    private final String roomName;

    @Min(value = 1, message = "최소 게스트 인원은 1명입니다.")
    private final int maxGuest;

    @Min(value = 10000, message = "최저 숙박요금은 1만원 입니다.")
    private final int price;

    private final String description;

    @OptionValid
    @NotNull
    private final Option option;

    @AddressValid
    @NotNull
    private final Address address;

    @Builder
    public RoomRequest(Long hostId, String roomName, int maxGuest, int price, String description,
        Option option, Address address) {
        this.hostId = hostId;
        this.roomName = roomName;
        this.maxGuest = maxGuest;
        this.price = price;
        this.description = description;
        this.option = option;
        this.address = address;
    }

    public Room toEntity(Host host) {
        return Room.builder()
            .host(host)
            .roomName(this.getRoomName())
            .maxGuest(this.getMaxGuest())
            .price(this.getPrice())
            .description(this.getDescription())
            .option(this.getOption())
            .address(this.getAddress())
            .build();
    }
}