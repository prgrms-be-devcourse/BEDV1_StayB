package org.programmers.staybb.controller.search;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.ReservationRepository;
import org.programmers.staybb.repository.RoomRepository;
import org.programmers.staybb.repository.UserRepository;
import org.programmers.staybb.service.RoomService;
import org.programmers.staybb.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AutoConfigureMockMvc
@SpringBootTest
class SearchControllerTest {
    @Autowired
    private SearchController searchController;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoomService roomService;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    private RoomRequest roomRequest;
    private Long roomId;

    @BeforeEach
    void setup() throws Exception{
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
            .endDate(LocalDate.of(2021, 12, 6))
            .guest(new Guest(1, 1, 1))
            .message("예약합니다")
            .user(user)
            .room(room1)
            .build();

        reservationRepository.save(reservation);
        reservationRepository.save(reservation1);
    }

    @Test
    @DisplayName("Search getAll 테스트")
    void getAllTest() throws Exception{
        mockMvc.perform(get("/v1/search")
                .param("location", "서울")
                .param("startDate", "2021-12-02")
                .param("endDate", "2021-12-05")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Search getOne 테스트")
    void getOneTest() throws Exception{
        mockMvc.perform(get("/v1/search/rooms/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}