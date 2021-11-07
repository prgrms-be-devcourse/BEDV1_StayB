package org.programmers.staybb.api;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.config.BaseIntegrationTest;
import org.programmers.staybb.setup.SearchSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

public class SearchControllerTest extends BaseIntegrationTest {

    @Autowired
    private SearchSetup searchSetup;

    @Test
    @DisplayName("[Search getAll] 예약날짜로만 조회")
    void getAllByCheckDateTest() throws Exception {
        //given
        searchSetup.saveReservations();
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/search")
                .param("startDate", "2021-12-06")
                .param("endDate", "2021-12-08")
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
    @DisplayName("[Search getAll] 예약날짜로와 지역으로 조회")
    void getAllByCheckDateAndRegionTest() throws Exception {
        //given
        searchSetup.saveReservations();
        searchSetup.saveRoom();
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/search")
                .param("startDate", "2021-12-06")
                .param("endDate", "2021-12-08")
                .param("location", "서초구")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("totalElements").value(1))
            .andDo(
                restDocs.document(
                    requestParameters(
                        parameterWithName("startDate").description("startDate"),
                        parameterWithName("endDate").description("endDate"),
                        parameterWithName("location").description("location"),
                        parameterWithName("page").description("page"),
                        parameterWithName("size").description("size")
                    ),
                    responseFields(
                        fieldWithPath("content[]").type(JsonFieldType.ARRAY)
                            .description("content[]"),
                        fieldWithPath("content[].id").type(JsonFieldType.NUMBER)
                            .description("예약 id"),
                        fieldWithPath("content[].roomName").type(JsonFieldType.STRING)
                            .description("숙소 이름"),
                        fieldWithPath("content[].maxGuest").type(JsonFieldType.NUMBER)
                            .description("숙박 가능한 최대 인원"),
                        fieldWithPath("content[].option").type(JsonFieldType.OBJECT)
                            .description("숙소 옵션"),
                        fieldWithPath("content[].option.bedNum").type(JsonFieldType.NUMBER)
                            .description("침대 수"),
                        fieldWithPath("content[].option.bedroomNum").type(JsonFieldType.NUMBER)
                            .description("침실 수"),
                        fieldWithPath("content[].option.bathroomNum").type(JsonFieldType.NUMBER)
                            .description("화장실 수"),
                        fieldWithPath("content[].region").type(JsonFieldType.STRING)
                            .description("지역 카테고리"),
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
    @DisplayName("[Search getAll] 예약날짜로와 지역과 숙박 인원으로 조회")
    void getAllByCheckDateAndRegionAndGuestNumTest() throws Exception {
        //given
        searchSetup.saveReservations();
        searchSetup.saveRoom();
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/search")
                .param("startDate", "2021-12-06")
                .param("endDate", "2021-12-08")
                .param("location", "서초구")
                .param("adult", "10")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("totalElements").value(0));

    }

}
