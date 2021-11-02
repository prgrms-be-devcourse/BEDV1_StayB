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
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.dto.search.SearchAllResponse;
import org.programmers.staybb.repository.HostRepository;
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

    private RoomRequest roomRequest;
    private Long roomId;

    @BeforeEach
    void setup() throws Exception{
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

        roomRequest = RoomRequest.builder()
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
        roomId = roomService.save(roomRequest);
    }

    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guest;
    private Option option;
    @Test
    @DisplayName("Search getAll 테스트")
    void getAllTest() throws Exception{
        mockMvc.perform(get("/v1/search")
                .param("location","서울")
                .param("adult","1")
                .param("bedNum","1")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Search getOne 테스트")
    void getOneTest() throws Exception{
        mockMvc.perform(get("/v1/search/{id}", roomId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}