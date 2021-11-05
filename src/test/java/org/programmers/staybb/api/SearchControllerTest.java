package org.programmers.staybb.api;

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
            .andExpect(jsonPath("totalElements").value(1));
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
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("totalElements").value(0));
    }

}
