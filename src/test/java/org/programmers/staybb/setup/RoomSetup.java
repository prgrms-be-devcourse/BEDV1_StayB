package org.programmers.staybb.setup;

import java.util.stream.IntStream;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomSetup {

    @Autowired
    private HostSetup hostSetup;

    @Autowired
    private RoomRepository roomRepository;

    public Room saveRoom() {
        return roomRepository.save(Room.builder()
            .roomName("이글루")
            .maxGuest(5)
            .price(10000)
            .description("어서오시오")
            .option(new Option(1, 1, 0.5))
            .address(new Address("서울시", "서울시 강남구 데브코스", "200동 111호"))
            .host(hostSetup.saveHost())
            .build());
    }

    public Long saveRooms() {
        Host host = hostSetup.saveHost();
        IntStream.range(0, 10).mapToObj(i -> Room.builder()
            .roomName("이글루")
            .maxGuest(5)
            .price(10000 * i)
            .description("어서오시오")
            .option(new Option(1, 1, 0.5))
            .address(new Address("서울시", "서울시 서초구 우면동", "200동 111호"))
            .host(host)
            .build()).forEach(room -> {
            roomRepository.save(room);
        });

        return host.getId();
    }

    public RoomRequest buildRoomRequest() {
        return RoomRequest.builder()
            .hostId(hostSetup.saveHost().getId())
            .roomName("이글루")
            .maxGuest(4)
            .price(10000)
            .description("어서오시오")
            .option(new Option(1, 1, 0.5))
            .address(new Address("서울시", "서울시 서초구 우면동", "200동 111호"))
            .build();
    }

}