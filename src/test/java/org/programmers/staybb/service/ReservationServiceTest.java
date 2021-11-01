package org.programmers.staybb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.Reservation.FindReservationByHostResponse;
import org.programmers.staybb.dto.Reservation.FindReservationsByUserResponse;
import org.programmers.staybb.dto.Reservation.ReservationSaveRequest;
import org.programmers.staybb.dto.Reservation.ReservationUpdateRequest;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.ReservationRepository;
import org.programmers.staybb.repository.RoomRepository;
import org.programmers.staybb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@Slf4j
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private RoomRepository roomRepository;

    private ReservationSaveRequest reservationDto;
    private Long reservationId;
    private Host saveHost;
    private User saveUser;
    private Room saveRoom;

    @BeforeEach
    void setUp() {
        User user = User.builder()
            .bio("하하")
            .birthday(LocalDate.of(1996, 07, 20))
            .email("sunho68@naver.com")
            .name("김선호")
            .phoneNumber("010-5306-6824")
            .build();

        saveUser = userRepository.save(user);

        Host host = Host.builder()
            .user(saveUser)
            .build();

        saveHost = hostRepository.save(host);

        Room room = Room.builder()
            .address(new Address("서울", "서울시 서초구 우면동 서초힐스", "201-0000"))
            .host(saveHost)
            .description("설명")
            .maxGuest(5)
            .price(1000)
            .option(new Option(1, 1, 0.5))
            .roomName("선호집")
            .build();

        saveRoom = roomRepository.save(room);

        reservationDto = ReservationSaveRequest.builder()
            .message("예약해여~")
            .adult(1)
            .teen(1)
            .child(1)
            .startDate(LocalDate.of(2021, 12, 1))
            .endDate(LocalDate.of(2021, 12, 5))
            .userId(saveUser.getId())
            .roomId(saveRoom.getId())
            .build();

        reservationId = reservationService.createReservation(reservationDto);

    }

    @Test
    @DisplayName("예약 수정")
    void updateResv() {
        ReservationUpdateRequest updateRequest = ReservationUpdateRequest.builder()
            .adult(1)
            .teen(1)
            .child(1)
            .startDate(LocalDate.of(2021, 12, 6))
            .endDate(LocalDate.of(2021, 12, 8))
            .build();
        Long aLong = reservationService.updateReservation(reservationId, updateRequest);
        assertThat(reservationRepository.findById(aLong).get().getStartDate()).isEqualTo(
            updateRequest.getStartDate());
    }

    @Test
    @DisplayName("호스트가 예약 단건 조회")
    void findOneByHostTest() {
        FindReservationByHostResponse oneByHost = reservationService.findOneByHost(reservationId);
        assertThat(oneByHost.getMessage()).isEqualTo("예약해여~)");

    }

    @Test
    @DisplayName("특정 사용자의 예약 전체 조회")
    void findAllByUserTest() {
        IntStream.range(1, 10).mapToObj(i -> ReservationSaveRequest.builder()
            .message("예약해여~")
            .adult(1)
            .teen(1)
            .child(1)
            .startDate(LocalDate.of(2021, 12, 1))
            .endDate(LocalDate.of(2021, 12, 5))
            .userId(saveUser.getId())
            .roomId(saveRoom.getId())
            .build()).forEach(request -> {
            reservationService.createReservation(request);
        });
        PageRequest pageable = PageRequest.of(0, 10);
        Page<FindReservationsByUserResponse> allByUser = reservationService.findAllByUser(
            saveUser.getId(), pageable);
        assertThat(allByUser.getTotalPages()).isEqualTo(1);
        assertThat(allByUser.getTotalElements()).isEqualTo(10);
    }

    @Test
    @DisplayName("특정 예약 삭제")
    void deletTest() {
        Long aLong = reservationService.deleteReservation(reservationId);

        assertThatThrownBy(() -> reservationService.findOneByHost(reservationId))
            .isInstanceOf(EntityNotFoundException.class);
    }


}