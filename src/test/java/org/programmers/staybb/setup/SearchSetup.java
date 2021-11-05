package org.programmers.staybb.setup;

import java.time.LocalDate;
import java.util.stream.IntStream;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.reservation.Reservation;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.repository.ReservationRepository;
import org.programmers.staybb.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchSetup {

    @Autowired
    private UserSetup userSetup;

    @Autowired
    private RoomSetup roomSetup;

    @Autowired
    private HostSetup hostSetup;

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private RoomRepository roomRepository;

    public void saveReservations() {
        User user = userSetup.saveUser();

        IntStream.range(0, 10).mapToObj(i -> Reservation.builder()
            .startDate(LocalDate.of(2021, 12, 6 + i))
            .endDate(LocalDate.of(2021, 12, 9 + i))
            .guest(new Guest(1, 1, 1))
            .message("안녕요")
            .user(user)
            .room(roomSetup.saveRoom())
            .build()).forEach(reservation -> {
            repository.save(reservation);
        });
    }

    public Room saveRoom() {
        return roomRepository.save(Room.builder()
            .roomName("프로그래머스")
            .maxGuest(5)
            .price(10000)
            .description("어서오시오")
            .option(new Option(1, 1, 0.5))
            .address(new Address("서울시", "서울시 서초구 데브코스", "200동 111호"))
            .host(hostSetup.saveHost())
            .build());
    }


}
