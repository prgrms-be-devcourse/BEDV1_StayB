package org.programmers.staybb.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
            .andExpect(jsonPath("id", is(notNullValue())));

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
            .andExpect(jsonPath("id").value(id));
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
                .content(objectMapper.writeValueAsString(updateInvalidInfo)))
            .andDo(print());
        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(user.getId()));
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
