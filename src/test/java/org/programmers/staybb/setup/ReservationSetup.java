package org.programmers.staybb.setup;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.reservation.Reservation;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.Reservation.GuestRequest;
import org.programmers.staybb.dto.Reservation.ReservationSaveRequest;
import org.programmers.staybb.dto.Reservation.ReservationUpdateRequest;
import org.programmers.staybb.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationSetup {

    @Autowired
    private UserSetup userSetup;

    @Autowired
    private RoomSetup roomSetup;

    @Autowired
    private ReservationRepository repository;

    public Reservation saveReservation() {
        return repository.save(Reservation.builder()
            .startDate(LocalDate.of(2021, 12, 6))
            .endDate(LocalDate.of(2021, 12, 9))
            .guest(new Guest(1, 1, 1))
            .message("안녕요")
            .user(userSetup.saveUser())
            .room(roomSetup.saveRoom())
            .build()
        );
    }

    public ReservationSaveRequest buildSaveRequest() {
        return ReservationSaveRequest.builder()
            .startDate(LocalDate.of(2021, 12, 6))
            .endDate(LocalDate.of(2021, 12, 9))
            .guestRequest(new GuestRequest(1, 1, 1))
            .message("안녕요")
            .userId(userSetup.saveUser().getId())
            .roomId(roomSetup.saveRoom().getId())
            .build();
    }

    public ReservationSaveRequest buildDummySaveRequest() {
        return ReservationSaveRequest.builder()
            .startDate(LocalDate.of(2021, 12, 6))
            .endDate(LocalDate.of(2021, 12, 9))
            .guestRequest(new GuestRequest(10, 10, 10))
            .message("안녕요")
            .userId(userSetup.saveUser().getId())
            .roomId(roomSetup.saveRoom().getId())
            .build();
    }

    public ReservationUpdateRequest buildUpdateRequest() {
        return ReservationUpdateRequest.builder()
            .startDate(LocalDate.of(2022, 1, 1))
            .endDate(LocalDate.of(2022, 2, 2))
            .guestRequest(new GuestRequest(3, 2, 1))
            .build();
    }

    public Map<String, Long> saveReservations() {
        User user = userSetup.saveUser();
        Room room = roomSetup.saveRoom();

        IntStream.range(0, 10).mapToObj(i -> Reservation.builder()
            .startDate(LocalDate.of(2021, 11, 3 + i))
            .endDate(LocalDate.of(2021, 11, 9 + i))
            .guest(new Guest(1, 1, 1))
            .message("안녕요")
            .user(user)
            .room(room)
            .build()).forEach(reservation -> {
            repository.save(reservation);
        });

        Map<String, Long> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("roomId", room.getId());

        return map;
    }

}
