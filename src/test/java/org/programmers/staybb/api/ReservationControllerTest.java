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
    @DisplayName("?????? ??????")
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
                        getDateFormat()).description("?????? ??????"),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ??????"),
                    fieldWithPath("guestRequest").type(JsonFieldType.OBJECT).description("??? ?????? ??????"),
                    fieldWithPath("guestRequest.adult").type(JsonFieldType.NUMBER)
                        .description("?????? ???"),
                    fieldWithPath("guestRequest.teen").type(JsonFieldType.NUMBER)
                        .description("????????? ???"),
                    fieldWithPath("guestRequest.child").type(JsonFieldType.NUMBER)
                        .description("?????? ???"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("????????? id"),
                    fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("?????? id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? id")
                )));
    }

    @Test
    @DisplayName("?????? ??????")
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
                    parameterWithName("id").description("?????? id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("????????? ?????? id")
                )
            ));
    }

    @Test
    @DisplayName("?????? ??????")
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
                    parameterWithName("id").description("?????? id")
                ),
                requestFields(
                    fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ??????"),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ??????"),
                    fieldWithPath("guestRequest").type(JsonFieldType.OBJECT).description("??? ?????? ??????"),
                    fieldWithPath("guestRequest.adult").type(JsonFieldType.NUMBER)
                        .description("?????? ???"),
                    fieldWithPath("guestRequest.teen").type(JsonFieldType.NUMBER)
                        .description("????????? ???"),
                    fieldWithPath("guestRequest.child").type(JsonFieldType.NUMBER)
                        .description("?????? ???")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("????????? ?????? id")
                )
            ));
    }

    @Test
    @DisplayName("?????? ????????? ????????? ?????? ?????? ????????? ??????")
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
                    parameterWithName("roomId").description("?????? id")
                ),
                responseFields(
                    fieldWithPath("[].reservationId").type(JsonFieldType.NUMBER)
                        .description("?????? id"),
                    fieldWithPath("[].checkDate").type(JsonFieldType.OBJECT).description("?????? ??????"),
                    fieldWithPath("[].checkDate.startDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ??????"),
                    fieldWithPath("[].checkDate.endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ??????")
                )
            ));
    }

    @Test
    @DisplayName("?????? ????????? ?????? ?????? ??????")
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
                    parameterWithName("userId").description("????????? id")
                ),
                requestParameters(
                    parameterWithName("page").optional().description("page"),
                    parameterWithName("size").optional().description("size")
                ),
                responseFields(
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("?????? id"),
                    fieldWithPath("content[].startDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ??????"),
                    fieldWithPath("content[].endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ??????"),
                    fieldWithPath("content[].roomRegion").type(JsonFieldType.STRING)
                        .description("?????? ?????? ????????????"),
                    fieldWithPath("content[].roomName").type(JsonFieldType.STRING)
                        .description("?????? ??????"),
                    fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER)
                        .description("pageNumber"),
                    fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER)
                        .description("pageSize"),
                    fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("empty ??????"),
                    fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("sorted ??????"),
                    fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("unsorted ??????"),
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
    @DisplayName("???????????? ???????????? ?????? ?????? ??????")
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
                    parameterWithName("id").description("?????? id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? id"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("????????? id"),
                    fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ??????"),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ??????"),
                    fieldWithPath("guest.adult").type(JsonFieldType.NUMBER).description("?????? ???"),
                    fieldWithPath("guest.teen").type(JsonFieldType.NUMBER).description("????????? ???"),
                    fieldWithPath("guest.kid").type(JsonFieldType.NUMBER).description("?????? ???"),
                    fieldWithPath("guest.totalGuest").type(JsonFieldType.NUMBER)
                        .description("??? ?????? ???"),
                    fieldWithPath("totalPrice").type(JsonFieldType.NUMBER).description("??? ?????? ???"),
                    fieldWithPath("roomName").type(JsonFieldType.STRING)
                        .description("?????? ??????"),
                    fieldWithPath("userName").type(JsonFieldType.STRING)
                        .description("????????? ????????? ??????"),
                    fieldWithPath("userPhoneNumber").type(JsonFieldType.STRING).attributes(
                            field("format", "{2,3}-{3,4}-{4}"))
                        .description("????????? ????????? ????????????"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("?????? ?????????"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ?????? ??????")
                )
            ));
    }

    @Test
    @DisplayName("???????????? ????????? ?????? ?????? ?????? ??????")
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
                    parameterWithName("id").description("?????? id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? id"),
                    fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("?????? id"),
                    fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ??????"),
                    fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("?????? ??????"),
                    fieldWithPath("guest.adult").type(JsonFieldType.NUMBER).description("?????? ???"),
                    fieldWithPath("guest.teen").type(JsonFieldType.NUMBER).description("????????? ???"),
                    fieldWithPath("guest.kid").type(JsonFieldType.NUMBER).description("?????? ???"),
                    fieldWithPath("guest.totalGuest").type(JsonFieldType.NUMBER)
                        .description("??? ?????? ???"),
                    fieldWithPath("totalPrice").type(JsonFieldType.NUMBER).description("??? ?????? ???"),
                    fieldWithPath("address.region").type(JsonFieldType.STRING).attributes(
                            field("format", "??? ??????"))
                        .description("?????? ????????????"),
                    fieldWithPath("address.address").type(JsonFieldType.STRING)
                        .attributes(field("format", "??? ???(???) ???"))
                        .description("?????? ??????"),
                    fieldWithPath("address.detailAddress").type(JsonFieldType.STRING)
                        .description("?????? ??????"),
                    fieldWithPath("roomName").type(JsonFieldType.STRING)
                        .description("?????? ??????"),
                    fieldWithPath("hostName").type(JsonFieldType.STRING)
                        .description("????????? ??????"),
                    fieldWithPath("hostPhoneNumber").type(JsonFieldType.STRING).attributes(
                            field("format", "{2,3}-{3,4}-{4}"))
                        .description("????????? ????????????")
                )
            ));
    }

    @Test
    @DisplayName("??????????????? ????????? maxGuest ????????? ?????? ???????????? ???????????? ??????.")
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
