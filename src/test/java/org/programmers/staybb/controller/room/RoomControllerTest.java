package org.programmers.staybb.controller.room;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.RoomRepository;
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
    private RoomRepository roomRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private UserRepository userRepository;

    private RoomRequest roomRequest;

    private User user;
    private Host host;
    private List<Room> roomList;

    @BeforeEach
    void setup() {
        //User 등록
        user = User.builder()
            .name("변민지")
            .birthday(LocalDate.of(1996, 01, 01))
            .email("bnminji@gmail.com")
            .phoneNumber("01050483601")
            .bio("변민지입니다")
            .build();
        userRepository.save(user);

        //Host 등록
        host = Host.builder()
            .user(user)
            .build();
        hostRepository.save(host);

        //Room 10개 저장
        roomList = IntStream.range(1, 11).mapToObj(i -> roomRepository.save(Room.builder()
            .roomName("beautiful apt in Seoul")
            .host(host)
            .maxGuest(i)
            .price(20000)
            .description("visit and enjoy beautiful apt in Seoul!")
            .option(Option.builder()
                .bedNum(i)
                .bedroomNum(i)
                .bathroomNum(0.5)
                .build()
            )
            .address(Address.builder()
                .region("서울시")
                .address(MessageFormat.format("서울시 중구 세종대로 {0}", i))
                .detailAddress(String.valueOf(i))
                .build()
            )
            .build())).collect(Collectors.toList());

        roomRequest = RoomRequest.builder()
            .hostId(host.getId())
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
                .region("서울시")
                .address("서울시 중구 세종대로 110")
                .detailAddress("11")
                .build()
            )
            .build();
    }


    @Test
    @DisplayName("Room save 테스트")
    void saveTest() throws Exception {
        mockMvc.perform(post("/v1/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomRequest)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void deleteTest() throws Exception {
        Long roomId = roomService.save(roomRequest);
        mockMvc.perform(delete("/v1/rooms/{roomId}", roomId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("호스트는 본인이 등록한 숙소의 아이디로 숙소 정보를 조회할 수 있다.")
    @Test
    void find() throws Exception {
        Room room = roomList.get(0);
        mockMvc.perform(get(MessageFormat.format("/v1/rooms/host/{0}", room.getId())).contentType(
                MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.roomName").value(room.getRoomName()))
            .andExpect(jsonPath("$.maxGuest").value(room.getMaxGuest()))
            .andExpect(jsonPath("$.price").value(room.getPrice()))
            .andExpect(jsonPath("$.description").value(room.getDescription()))
            .andExpect(jsonPath("$.option").value(objectMapper.convertValue(room.getOption(),
                LinkedHashMap.class)))
            .andExpect(jsonPath("$.address").value(
                objectMapper.convertValue(room.getAddress(), LinkedHashMap.class)))
            .andDo(print());
    }

    @DisplayName("호스트는 본인이 등록한 숙소 리스트를 페이지 단위로 조회할 수 있다.")
    @Test
    void findAll() throws Exception {
        mockMvc.perform(
                get(MessageFormat.format("/v1/rooms?hostId={0}&size={1}&page={2}",
                    host.getId(), 15, 0))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.numberOfElements").value(roomList.size()));
    }

    @DisplayName("호스트는 본인이 등록한 숙소의 정보를 요청 1회당 1개의 필드를 변경할 수 있다.")
    @Test
    void updateSingleField() throws Exception {
        Map<String, Object> updateInfo = new HashMap<>();
        updateInfo.put("roomName", "ABC아파트");
        mockMvc.perform(
                patch("/v1/rooms/3").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateInfo)))
            .andExpect(status().isOk());
    }

    @DisplayName("숙소 정보 변경시 Room Entity 필드에 없는 정보는 수정할 수 없다.")
    @Test
    void updateInvalidField() throws Exception {
        Map<String, Object> updateInvalidInfo = new HashMap<>();
        updateInvalidInfo.put("roomName22", "ABC아파트");
        mockMvc.perform(
                patch("/v1/rooms/3").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateInvalidInfo)))
            .andExpect(status().isOk());
    }

}