package org.programmers.staybb.controller.room;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.UserRepository;
import org.programmers.staybb.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class RoomControllerTest {
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

    @BeforeEach
    void setup() {
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
    }


    @Test
    @DisplayName("Room save 테스트")
    void saveTest() throws Exception{
        mockMvc.perform(post("/v1/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomRequest)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void deleteTest() throws Exception{
        Long roomId = roomService.save(roomRequest);
        mockMvc.perform(delete("/v1/rooms/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }
}