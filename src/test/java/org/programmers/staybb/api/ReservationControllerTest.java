package org.programmers.staybb.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.config.BaseIntegrationTest;
import org.programmers.staybb.domain.reservation.Reservation;
import org.programmers.staybb.dto.Reservation.ReservationSaveRequest;
import org.programmers.staybb.dto.Reservation.ReservationUpdateRequest;
import org.programmers.staybb.global.exception.ErrorCode;
import org.programmers.staybb.setup.ReservationSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

public class ReservationControllerTest extends BaseIntegrationTest {

    @Autowired
    private ReservationSetup reservationSetup;

    @Test
    @DisplayName("예약 생성")
    public void createReservation() throws Exception {
        //given
        ReservationSaveRequest saveRequest = reservationSetup.buildSaveRequest();
        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saveRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id", is(notNullValue())));
    }

    @Test
    @DisplayName("예약 삭제")
    void deleteReservation() throws Exception {
        //given
        Long id = reservationSetup.saveReservation().getId();
        //when
        ResultActions resultActions = mockMvc.perform(delete("/v1/reservation/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(id));
    }

    @Test
    @DisplayName("예약 수정")
    void updateReservation() throws Exception {
        //given
        Long id = reservationSetup.saveReservation().getId();
        ReservationUpdateRequest updateRequest = reservationSetup.buildUpdateRequest();
        //when
        ResultActions resultActions = mockMvc.perform(put("/v1/reservation/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(id));
    }

    @Test
    @DisplayName("특정 숙소에 등록된 예약 날짜 리스트 조회")
    void findAllCheckDate() throws Exception {
        //given
        Map<String, Long> idMap = reservationSetup.saveReservations();
        Long roomId = idMap.get("roomId");
        //when
        ResultActions resultActions = mockMvc.perform(
                get("/v1/reservation/checkDate/{roomId}", roomId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저 등록한 예약 전체 조회")
    void findAllByGuest() throws Exception {
        //given
        Map<String, Long> idMap = reservationSetup.saveReservations();
        Long userId = idMap.get("userId");
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/reservation/{userId}", userId)
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("totalElements").value(8));
    }

    @Test
    @DisplayName("호스트가 신청받은 예약 세부 조회")
    void findOneByHost() throws Exception {
        //given
        Reservation reservation = reservationSetup.saveReservation();
        Long id = reservation.getId();
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/reservation/host/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("message").value(reservation.getMessage()))
            .andExpect(jsonPath("startDate").value(String.valueOf(reservation.getStartDate())))
            .andExpect(jsonPath("totalPrice").value(reservation.getTotalPrice()))
            .andExpect(jsonPath("roomName").value(reservation.getRoom().getRoomName()))
            .andExpect(jsonPath("userName").value(reservation.getUser().getName()))
            .andExpect(jsonPath("userPhoneNumber").value(reservation.getUser().getPhoneNumber()));
    }

    @Test
    @DisplayName("게스트가 등록한 특정 예약 세부 조회")
    void findOneByGuest() throws Exception {
        //given
        Reservation reservation = reservationSetup.saveReservation();
        Long id = reservation.getId();
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/reservation/guest/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("startDate").value(String.valueOf(reservation.getStartDate())))
            .andExpect(jsonPath("totalPrice").value(reservation.getTotalPrice()))
            .andExpect(jsonPath("roomName").value(reservation.getRoom().getRoomName()))
            .andExpect(
                jsonPath("hostName").value(reservation.getRoom().getHost().getUser().getName()))
            .andExpect(jsonPath("hostPhoneNumber").value(
                reservation.getRoom().getHost().getUser().getPhoneNumber()));
    }

    @Test
    @DisplayName("예약하려는 숙소의 maxGuest 숫자를 넘는 인원으로 예약할수 없다.")
    void validMaxguest() throws Exception {
        //given
        ReservationSaveRequest dummySaveRequest = reservationSetup.buildDummySaveRequest();

        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dummySaveRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("message").value(ErrorCode.OVER_CROWDING.getMessage()));
    }
}
