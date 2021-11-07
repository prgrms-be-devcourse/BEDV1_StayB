package org.programmers.staybb.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.programmers.staybb.config.RestDocsConfiguration.field;
import static org.programmers.staybb.config.RestDocsConfiguration.getDateFormat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

public class ReservationControllerTest extends BaseIntegrationTest {

    @Autowired
    private ReservationSetup reservationSetup;

    @Test
    @DisplayName("예약 생성")
    void createReservation() throws Exception {
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
            .andExpect(jsonPath("id", is(notNullValue())))
            .andDo(restDocs.document(
                requestFields(
                    fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("입실 날짜"),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("퇴실 날짜"),
                    fieldWithPath("guestRequest").type(JsonFieldType.OBJECT).description("총 숙박 인원"),
                    fieldWithPath("guestRequest.adult").type(JsonFieldType.NUMBER)
                        .description("성인 수"),
                    fieldWithPath("guestRequest.teen").type(JsonFieldType.NUMBER)
                        .description("청소년 수"),
                    fieldWithPath("guestRequest.child").type(JsonFieldType.NUMBER)
                        .description("아이 수"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("예약 메세지"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게스트 id"),
                    fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("숙소 id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("예약 id")
                )));
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
            .andExpect(jsonPath("id").value(id))
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("id").description("예약 id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("삭제된 예약 id")
                )
            ));
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
                .content(objectMapper.writeValueAsString(updateRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(id))
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("id").description("예약 id")
                ),
                requestFields(
                    fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("입실 날짜"),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("퇴실 날짜"),
                    fieldWithPath("guestRequest").type(JsonFieldType.OBJECT).description("총 숙박 인원"),
                    fieldWithPath("guestRequest.adult").type(JsonFieldType.NUMBER)
                        .description("성인 수"),
                    fieldWithPath("guestRequest.teen").type(JsonFieldType.NUMBER)
                        .description("청소년 수"),
                    fieldWithPath("guestRequest.child").type(JsonFieldType.NUMBER)
                        .description("아이 수")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("수정된 예약 id")
                )
            ));
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
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("roomId").description("숙소 id")
                ),
                responseFields(
                    fieldWithPath("[].reservationId").type(JsonFieldType.NUMBER)
                        .description("예약 id"),
                    fieldWithPath("[].checkDate").type(JsonFieldType.OBJECT).description("예약 날짜"),
                    fieldWithPath("[].checkDate.startDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("입실 날짜"),
                    fieldWithPath("[].checkDate.endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("퇴실 날짜")
                )
            ));
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
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("totalElements").value(5))
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("userId").description("사용자 id")
                ),
                requestParameters(
                    parameterWithName("page").optional().description("page"),
                    parameterWithName("size").optional().description("size")
                ),
                responseFields(
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("예약 id"),
                    fieldWithPath("content[].startDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("입실 날짜"),
                    fieldWithPath("content[].endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("퇴실 날짜"),
                    fieldWithPath("content[].roomRegion").type(JsonFieldType.STRING)
                        .description("숙소 지역 카테고리"),
                    fieldWithPath("content[].roomName").type(JsonFieldType.STRING)
                        .description("숙소 이름"),
                    fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER)
                        .description("pageNumber"),
                    fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER)
                        .description("pageSize"),
                    fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("empty 여부"),
                    fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("sorted 여부"),
                    fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("unsorted 여부"),
                    fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER)
                        .description("offset"),
                    fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN)
                        .description("paged"),
                    fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN)
                        .description("unpaged"),
                    fieldWithPath(".totalPages").type(JsonFieldType.NUMBER)
                        .description("totalPages"),
                    fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                    fieldWithPath("number").type(JsonFieldType.NUMBER)
                        .description("number"),
                    fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("empty"),
                    fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("sorted"),
                    fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("unsorted"),
                    fieldWithPath("first").type(JsonFieldType.BOOLEAN)
                        .description("first"),
                    fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
                    fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER)
                        .description("numberOfElements"),
                    fieldWithPath("totalElements").type(JsonFieldType.NUMBER)
                        .description("totalElements"),
                    fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty")
                )));
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
            .andExpect(jsonPath("userPhoneNumber").value(reservation.getUser().getPhoneNumber()))
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("id").description("예약 id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("예약 id"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게스트 id"),
                    fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("입실 날짜"),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("퇴실 날짜"),
                    fieldWithPath("guest.adult").type(JsonFieldType.NUMBER).description("어른 수"),
                    fieldWithPath("guest.teen").type(JsonFieldType.NUMBER).description("청소년 수"),
                    fieldWithPath("guest.kid").type(JsonFieldType.NUMBER).description("아이 수"),
                    fieldWithPath("guest.totalGuest").type(JsonFieldType.NUMBER)
                        .description("총 인원 수"),
                    fieldWithPath("totalPrice").type(JsonFieldType.NUMBER).description("총 숙박 비"),
                    fieldWithPath("roomName").type(JsonFieldType.STRING)
                        .description("숙소 이름"),
                    fieldWithPath("userName").type(JsonFieldType.STRING)
                        .description("예약한 게스트 이름"),
                    fieldWithPath("userPhoneNumber").type(JsonFieldType.STRING).attributes(
                            field("format", "{2,3}-{3,4}-{4}"))
                        .description("예약한 게스트 전화번호"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("예약 메세지"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("예약 생성 날짜")
                )
            ));
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
                reservation.getRoom().getHost().getUser().getPhoneNumber()))
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("id").description("예약 id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("예약 id"),
                    fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("숙소 id"),
                    fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("입실 날짜"),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("퇴실 날짜"),
                    fieldWithPath("guest.adult").type(JsonFieldType.NUMBER).description("어른 수"),
                    fieldWithPath("guest.teen").type(JsonFieldType.NUMBER).description("청소년 수"),
                    fieldWithPath("guest.kid").type(JsonFieldType.NUMBER).description("아이 수"),
                    fieldWithPath("guest.totalGuest").type(JsonFieldType.NUMBER)
                        .description("총 인원 수"),
                    fieldWithPath("totalPrice").type(JsonFieldType.NUMBER).description("총 숙박 비"),
                    fieldWithPath("address.region").type(JsonFieldType.STRING).attributes(
                            field("format", "시 단위"))
                        .description("지역 카테고리"),
                    fieldWithPath("address.address").type(JsonFieldType.STRING)
                        .attributes(field("format", "시 구(군) 구"))
                        .description("숙소 주소"),
                    fieldWithPath("address.detailAddress").type(JsonFieldType.STRING)
                        .description("상세 주소"),
                    fieldWithPath("roomName").type(JsonFieldType.STRING)
                        .description("숙소 이름"),
                    fieldWithPath("hostName").type(JsonFieldType.STRING)
                        .description("호스트 이름"),
                    fieldWithPath("hostPhoneNumber").type(JsonFieldType.STRING).attributes(
                            field("format", "{2,3}-{3,4}-{4}"))
                        .description("호스트 전화번호")
                )
            ));
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
