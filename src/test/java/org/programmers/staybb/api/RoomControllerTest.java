package org.programmers.staybb.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.config.BaseIntegrationTest;
import org.programmers.staybb.domain.room.Address;
import org.programmers.staybb.domain.room.Option;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.global.exception.ErrorCode;
import org.programmers.staybb.setup.RoomSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

public class RoomControllerTest extends BaseIntegrationTest {

    @Autowired
    private RoomSetup roomSetup;

    @Test
    @DisplayName("숙소 생성")
    public void createRoom() throws Exception {
        //given
        RoomRequest roomRequest = roomSetup.buildRoomRequest();
        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id", is(notNullValue())));

    }

    @Test
    @DisplayName("숙소 삭제")
    void deleteRoom() throws Exception {
        //given
        Long id = roomSetup.saveRoom().getId();
        //when
        ResultActions resultActions = mockMvc.perform(delete("/v1/rooms/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(id));
    }

    @Test
    @DisplayName("숙소 세부 조회")
    void findOneRoom() throws Exception {
        //given
        Room room = roomSetup.saveRoom();
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/rooms/{roomId}", room.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("roomName").value(room.getRoomName()))
            .andExpect(jsonPath("maxGuest").value(room.getMaxGuest()))
            .andExpect(jsonPath("price").value(room.getPrice()))
            .andExpect(jsonPath("description").value(room.getDescription()));
    }

    @Test
    @DisplayName("호스트가 등록한 숙소 전체 조회")
    void findAllByHost() throws Exception {
        //given
        Long hostId = roomSetup.saveRooms();
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/rooms")
                .param("hostId", String.valueOf(hostId))
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("totalElements").value(10));
    }

    @Test
    @DisplayName("숙소 정보 Address 변경 / Patch Test")
    void updateAllRoom() throws Exception {
        //given
        Room room = roomSetup.saveRoom();
        Address updatedAddress = new Address("부산광역시", "해운대 앞바다", "세븐일레븐");
        //when
        ResultActions resultActions = mockMvc.perform(patch("/v1/rooms/{id}", room.getId())
                .param("field", "address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAddress)))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(room.getId()));
    }

    @Test
    @DisplayName("잘못된 숙소 정보 입력")
    void validRoomRequest() throws Exception {
        //given
        Long hostId = roomSetup.saveRoom().getHost().getId();
        RoomRequest roomRequest = RoomRequest.builder()
            .hostId(hostId)
            .roomName(" ")
            .maxGuest(0)
            .price(100)
            .description("어서오시오")
            .option(new Option(0, 0, 0))
            .address(new Address("서울", " ", "200동 111호"))
            .build();
        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }

}