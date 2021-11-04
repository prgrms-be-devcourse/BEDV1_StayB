package org.programmers.staybb.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sun.istack.NotNull;
import java.time.LocalDate;
import java.util.List;
import javassist.NotFoundException;
import javax.persistence.ConstraintMode;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.domain.reservation.Guest;
import org.programmers.staybb.domain.reservation.Reservation;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.dto.search.SearchAllResponse;
import org.programmers.staybb.dto.search.SearchOneResponse;
import org.programmers.staybb.dto.search.SearchRequest;
import org.programmers.staybb.dto.search.SearchRequestModel;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.ReservationRepository;
import org.programmers.staybb.repository.RoomRepository;
import org.programmers.staybb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Slf4j
@SpringBootTest
class SearchServiceTest {
    @Autowired
    private SearchService searchService;
    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() throws NotFoundException {
    }

    @Test
    void findOneTest() throws NotFoundException {
        //Given
        //User 등록
        User user = User.builder()
            .name("변민지")
            .birthday(LocalDate.of(1996,01,01))
            .email("bnminji@gmail.com")
            .phoneNumber("01050483601")
            .bio("변민지입니다")
            .build();
        userRepository.save(user);

        //Host 등록
        Host host = Host.builder()
            .user(user)
            .build();
        hostRepository.save(host);

        //Room 등록
        Long hostId = 2L;

        RoomRequest roomRequest = RoomRequest.builder()
            .hostId(hostId)
            .roomName("beautiful apt in Seoul")
            .maxGuest(2)
            .price(20000)
            .description("visit and enjoy beautiful apt in Seoul!")
            .option(Option.builder()
                .bedNum(1)
                .bedroomNum(1)
                .bathroomNum(0.5)
                .build()
            )
            .address(Address.builder()
                .region("서울")
                .address("서울 중구 세종대로 110")
                .detail_address("11")
                .build()
            )
            .build();

        //When
        Long savedRoomId = roomService.save(roomRequest);
        // Given
        Long id = 3L;
        // When
        SearchOneResponse one = searchService.findOne(id);
        // Then
        assertThat(one.getId()).isEqualTo(id);
    }

    @Test
    void findAllTest() throws NotFoundException{
        //Given
        //User 등록
        User user = User.builder()
            .name("변민지")
            .birthday(LocalDate.of(1996,01,01))
            .email("bnminji@gmail.com")
            .phoneNumber("01050483601")
            .bio("변민지입니다")
            .build();
        userRepository.save(user);

        //Host 등록
        Host host = Host.builder()
            .user(user)
            .build();
        hostRepository.save(host);

        ////
        Long hostId = 2L;
        //Room 등록

        Room room = Room.builder()
            .roomName("beautiful apt in Seoul")
            .address(Address.builder()
                .region("서울")
                .address("서울 중구 세종대로 110")
                .detail_address("11")
                .build()
            )
            .maxGuest(1)
            .option(Option.builder()
                .bedNum(1)
                .bedroomNum(1)
                .bathroomNum(1)
                .build()
            )
            .description("visit and enjoy beautiful apt in Seoul!")
            .host(host)
            .price(20000)
            .build();

        Room room1 = Room.builder()
            .roomName("beautiful apt in Busan")
            .address(Address.builder()
                .region("부산")
                .address("부산 연제구 중앙대로 1001")
                .detail_address("11")
                .build()
            )
            .maxGuest(2)
            .option(Option.builder()
                .bedNum(2)
                .bedroomNum(2)
                .bathroomNum(2)
                .build()
            )
            .description("visit and enjoy beautiful apt in Busan!")
            .host(host)
            .price(20000)
            .build();

        Room room2 = Room.builder()
            .roomName("beautiful apt in Seoul Jung-gu")
            .address(Address.builder()
                .region("서울")
                .address("서울 중구 세종대로 110")
                .detail_address("11")
                .build()
            )
            .maxGuest(3)
            .option(Option.builder()
                .bedNum(3)
                .bedroomNum(3)
                .bathroomNum(3)
                .build()
            )
            .description("visit and enjoy beautiful apt in Seoul, Jung-gu!")
            .host(host)
            .price(20000)
            .build();

        roomRepository.save(room);
        roomRepository.save(room1);
        roomRepository.save(room2);

        //Reservation 등록
        Reservation reservation = Reservation.builder()
            .startDate(LocalDate.of(2021, 12, 1))
            .endDate(LocalDate.of(2021, 12, 5))
            .guest(new Guest(1, 1, 1))
            .message("예약합니다")
            .user(user)
            .room(room)
            .build();

        Reservation reservation1 = Reservation.builder()
            .startDate(LocalDate.of(2021, 12, 5))
            .endDate(LocalDate.of(2021, 12, 7))
            .guest(new Guest(1, 1, 1))
            .message("예약합니다")
            .user(user)
            .room(room1)
            .build();

        reservationRepository.save(reservation);
        reservationRepository.save(reservation1);

        // Given
        // Scenario1 - location, result = 2L
        SearchRequestModel searchRequestModel = new SearchRequestModel();
        searchRequestModel.setLocation("서울");

        // Scenario2 - totalGuest, result = 2L
        SearchRequestModel searchRequestModel1 = new SearchRequestModel();
        searchRequestModel1.setAdult(2);

        // Scenario3 - option, result = 2L
        SearchRequestModel searchRequestModel2 = new SearchRequestModel();
        searchRequestModel2.setBedNum(2);
        searchRequestModel2.setBathroomNum(2);

        // Scenario4 - 통합, result = 1L
        SearchRequestModel searchRequestModel3 = new SearchRequestModel();
        searchRequestModel3.setLocation("서울");
        searchRequestModel3.setStartDate("2021-12-02");
        searchRequestModel3.setEndDate("2021-12-05");

        // **service test
        SearchRequest searchRequest = new SearchRequest(searchRequestModel);
        SearchRequest searchRequest1 = new SearchRequest(searchRequestModel1);
        SearchRequest searchRequest2 = new SearchRequest(searchRequestModel2);
        SearchRequest searchRequest3 = new SearchRequest(searchRequestModel3);

        PageRequest page = PageRequest.of(0, 10);
        // When
        Page<SearchAllResponse> all = searchService.findByFilters(searchRequest, page);
        Page<SearchAllResponse> all1 = searchService.findByFilters(searchRequest1, page);
        Page<SearchAllResponse> all2 = searchService.findByFilters(searchRequest2, page);
        Page<SearchAllResponse> all3 = searchService.findByFilters(searchRequest3, page);


        // Then
        assertThat(all.getTotalElements()).isEqualTo(2L);
        assertThat(all1.getTotalElements()).isEqualTo(2L);
        assertThat(all2.getTotalElements()).isEqualTo(2L);
        assertThat(all3.getTotalElements()).isEqualTo(1L);

        List<SearchAllResponse> list = all3.getContent();
//        log.info("Result : {} {}", list.get(0).getRoomName(), list.get(1).getRoomName());
        log.info("Result : {}", list.get(0).getRoomName());

    }
}