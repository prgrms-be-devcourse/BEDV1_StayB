package org.programmers.staybb.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.programmers.staybb.config.RestDocsConfiguration.field;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

public class RoomControllerTest extends BaseIntegrationTest {

    @Autowired
    private RoomSetup roomSetup;

    @Test
    @DisplayName("?????? ??????")
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
            .andExpect(jsonPath("id", is(notNullValue())))
            .andDo(
                restDocs.document(
                    requestFields(
                        fieldWithPath("hostId").type(JsonFieldType.NUMBER)
                            .description("????????? ID"),
                        fieldWithPath("roomName").type(JsonFieldType.STRING)
                            .description("?????? ??????"),
                        fieldWithPath("maxGuest").type(JsonFieldType.NUMBER)
                            .description("?????? ????????? ?????? ?????? ???"),
                        fieldWithPath("price").type(JsonFieldType.NUMBER)
                            .description("1?????? ??????"),
                        fieldWithPath("description").type(JsonFieldType.STRING)
                            .description("?????? ?????? ???"),
                        fieldWithPath("option").type(JsonFieldType.OBJECT)
                            .description("?????? ??????"),
                        fieldWithPath("option.bedNum").type(JsonFieldType.NUMBER)
                            .description("?????? ???"),
                        fieldWithPath("option.bedroomNum").type(JsonFieldType.NUMBER)
                            .description("?????? ???"),
                        fieldWithPath("option.bathroomNum").type(JsonFieldType.NUMBER).attributes(
                                field("format", "0.5??????"))
                            .description("????????? ???"),
                        fieldWithPath("address").type(JsonFieldType.OBJECT).attributes(
                                field("format", "0.5??????"))
                            .description("?????? ??????"),
                        fieldWithPath("address.region").type(JsonFieldType.STRING).attributes(
                                field("format", "??? ??????"))
                            .description("?????? ????????????"),
                        fieldWithPath("address.address").type(JsonFieldType.STRING).attributes(
                                field("format", "??? ???(???) ???"))
                            .description("?????? ??????"),
                        fieldWithPath("address.detailAddress").type(JsonFieldType.STRING)
                            .description("?????? ??????")
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("????????? ?????? id")
                    )
                )
            );

    }

    @Test
    @DisplayName("?????? ??????")
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
            .andExpect(jsonPath("id").value(id))
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("id").description("???????????? ?????? ?????? id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("????????? ?????? id")
                )
            ));

    }

    @Test
    @DisplayName("?????? ?????? ??????")
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
            .andExpect(jsonPath("description").value(room.getDescription()))
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("roomId").description("????????? ?????? id")
                ),
                responseFields(
                    fieldWithPath("roomName").type(JsonFieldType.STRING)
                        .description("?????? ??????"),
                    fieldWithPath("maxGuest").type(JsonFieldType.NUMBER)
                        .description("?????? ????????? ?????? ?????? ???"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER)
                        .description("1?????? ??????"),
                    fieldWithPath("description").type(JsonFieldType.STRING)
                        .description("?????? ?????? ???"),
                    fieldWithPath("option").type(JsonFieldType.OBJECT)
                        .description("?????? ??????"),
                    fieldWithPath("option.bedNum").type(JsonFieldType.NUMBER)
                        .description("?????? ???"),
                    fieldWithPath("option.bedroomNum").type(JsonFieldType.NUMBER)
                        .description("?????? ???"),
                    fieldWithPath("option.bathroomNum").type(JsonFieldType.NUMBER).attributes(
                            field("format", "0.5??????"))
                        .description("????????? ???"),
                    fieldWithPath("address").type(JsonFieldType.OBJECT)
                        .description("?????? ??????"),
                    fieldWithPath("address.region").type(JsonFieldType.STRING).attributes(
                            field("format", "??? ??????"))
                        .description("?????? ????????????"),
                    fieldWithPath("address.address").type(JsonFieldType.STRING)
                        .attributes(field("format", "??? ???(???) ???"))
                        .description("?????? ??????"),
                    fieldWithPath("address.detailAddress").type(JsonFieldType.STRING)
                        .description("?????? ??????")
                )
            ));
    }

    @Test
    @DisplayName("???????????? ????????? ?????? ?????? ??????")
    void findAllByHost() throws Exception {
        //given
        Long hostId = roomSetup.saveRooms();
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/rooms")
                .param("hostId", String.valueOf(hostId))
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("totalElements").value(10))
            .andDo(restDocs.document(
                requestParameters(
                    parameterWithName("hostId").description("????????? ID"),
                    parameterWithName("page").optional().description("page"),
                    parameterWithName("size").optional().description("size")
                ),
                responseFields(
                    fieldWithPath("content[].address").type(JsonFieldType.STRING)
                        .attributes(field("format", "??? ???(???) ???"))
                        .description("?????? ??????"),
                    fieldWithPath("content[].option").type(JsonFieldType.OBJECT)
                        .description("?????? ??????"),
                    fieldWithPath("content[].option.bedNum").type(JsonFieldType.NUMBER)
                        .description("?????? ???"),
                    fieldWithPath("content[].option.bedroomNum").type(JsonFieldType.NUMBER)
                        .description("?????? ???"),
                    fieldWithPath("content[].option.bathroomNum").type(JsonFieldType.NUMBER)
                        .attributes(
                            field("format", "0.5??????"))
                        .description("????????? ???"),
                    fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING)
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
    @DisplayName("?????? ?????? Address ?????? / Patch Test")
    void updateAllRoom() throws Exception {
        //given
        Room room = roomSetup.saveRoom();
        Address updatedAddress = new Address("???????????????", "????????? ?????????", "???????????????");
        //when
        ResultActions resultActions = mockMvc.perform(patch("/v1/rooms/{id}", room.getId())
                .param("field", "address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAddress))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(room.getId()))
            .andDo(
                restDocs.document(
                    pathParameters(
                        parameterWithName("id").description("?????? id")
                    ),
                    requestParameters(
                        parameterWithName("field").description("????????? ?????????")
                    ),
                    requestFields(
                        fieldWithPath("region").type(JsonFieldType.STRING).attributes(
                                field("format", "??? ??????"))
                            .description("?????? ????????????"),
                        fieldWithPath("address").type(JsonFieldType.STRING).attributes(
                                field("format", "format\",\"??? ???(???) ???"))
                            .description("?????? ??????"),
                        fieldWithPath("detailAddress").type(JsonFieldType.STRING)
                            .description("?????? ??????")
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("update??? ?????? id")
                    )
                )
            );
    }

    @Test
    @DisplayName("????????? ?????? ?????? ??????")
    void validRoomRequest() throws Exception {
        //given
        Long hostId = roomSetup.saveRoom().getHost().getId();
        RoomRequest roomRequest = RoomRequest.builder()
            .hostId(hostId)
            .roomName(" ")
            .maxGuest(0)
            .price(100)
            .description("???????????????")
            .option(new Option(0, 0, 0))
            .address(new Address("??????", " ", "200??? 111???"))
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