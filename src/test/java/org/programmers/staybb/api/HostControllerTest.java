package org.programmers.staybb.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.config.BaseIntegrationTest;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.global.exception.ErrorCode;
import org.programmers.staybb.setup.HostSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

public class HostControllerTest extends BaseIntegrationTest {

    @Autowired
    private HostSetup hostSetup;

    @Test
    @DisplayName("게스트가 호스트를 생성할 수 있다.")
    void createHost() throws Exception {
        //given
        Long userId = hostSetup.getUserId();
        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/host/add/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id", is(notNullValue())))
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("userId").description("사용자 id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("호스트 id")
                )
            ));
    }

    @Test
    @DisplayName("호스트 프로필을 조회할 수 있다.")
    void findHost() throws Exception {
        //given
        Host host = hostSetup.saveHost();
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/host/{hostId}", host.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value(host.getUser().getName()))
            .andExpect(jsonPath("superhost").value(false))
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("hostId").description("호스트 id")
                ),
                responseFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("호스트 이름"),
                    fieldWithPath("superhost").type(JsonFieldType.BOOLEAN)
                        .description("슈퍼호스트 여부"),
                    fieldWithPath("roomIds").type(JsonFieldType.ARRAY).description("호스트 id")
                )
            ));

    }

    @Test
    @DisplayName("존재하지 않는 userId로 Host를 생성할 수 없다.")
    void validFindUser() throws Exception {
        //given
        Long dummyUserId = 500L;
        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/host/add/{userId}", dummyUserId)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("message").value(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

}
