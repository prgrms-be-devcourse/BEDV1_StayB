package org.programmers.staybb.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.programmers.staybb.config.RestDocsConfiguration.field;
import static org.programmers.staybb.config.RestDocsConfiguration.getDateFormat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.config.BaseIntegrationTest;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.user.UserRequest;
import org.programmers.staybb.global.exception.ErrorCode;
import org.programmers.staybb.setup.UserSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

public class UserControllerTest extends BaseIntegrationTest {

    @Autowired
    private UserSetup userSetup;

    @Test
    @DisplayName("사용자 생성")
    public void createUser() throws Exception {
        //given
        UserRequest userRequest = userSetup.buildUserRequest();

        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id", is(notNullValue())))
            .andDo(restDocs.document(
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("birthday").type(JsonFieldType.STRING).attributes(
                        getDateFormat()).description("생년월일"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("전화번호")
                        .attributes(
                            field("format", "{2,3}-{3,4}-{4}")),
                    fieldWithPath("bio").type(JsonFieldType.STRING).description("자기소개").optional()
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("사용자 id")
                )));
    }

    @Test
    @DisplayName("사용자 삭제")
    void deleteUser() throws Exception {
        //given
        Long id = userSetup.saveUser().getId();
        //when
        ResultActions resultActions = mockMvc.perform(delete("/v1/user/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(id))
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("id").description("사용자 id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("삭제된 사용자 id")
                )
            ));
    }

    @Test
    @DisplayName("사용자 정보 email 변경 / Patch Test")
    void updateAllUser() throws Exception {
        //given
        User user = userSetup.saveUser();
        Map<String, Object> updateInvalidInfo = new HashMap<>();
        updateInvalidInfo.put("email", "updated@gmail.com");
        //when
        ResultActions resultActions = mockMvc.perform(patch("/v1/user/{id}", user.getId())
                .param("field", "email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateInvalidInfo))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(user.getId()))
            .andDo(
                restDocs.document(
                    pathParameters(
                        parameterWithName("id").description("사용자 id")
                    ),
                    requestParameters(
                        parameterWithName("field").description("변경할 필드명")
                    ),
                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING)
                            .description("변경하고 싶은 email")
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("update된 사용자 id")
                    )
                )
            );

    }

    @Test
    @DisplayName("잘못된 사용자 정보 입력")
    void validUserRequest() throws Exception {
        //given
        UserRequest userRequest = UserRequest.builder()
            .name("김선호")
            .birthday(LocalDate.of(2021, 12, 15))
            .phoneNumber("0000")
            .email("eeeee")
            .build();
        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        //then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }


}
