package org.programmers.staybb.controller.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.dto.user.UserRequest;
import org.programmers.staybb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;
    @Autowired
    private MockMvc mockMvc;
    private UserRequest userRequest;

    @BeforeEach
    void setup() {
        userRequest = UserRequest.builder()
            .email("sunho00@gmail.com")
            .phoneNumber("010-5535-5555")
            .name("김선호")
            .birthday(LocalDate.of(1996, 7, 20))
            .bio("안녕하세요")
            .build();
    }


    @Test
    @DisplayName("User add 테스트")
    void saveUser() throws Exception {
        mockMvc.perform(post("/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
            .andExpect(status().isOk()) // 200
            .andDo(print());
    }

    @Test
    @DisplayName("User remove 테스트")
    void removeUser() throws Exception {
        Long userId = createUser();
        mockMvc.perform(delete("/v1/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    private Long createUser() {
        return userService.addUser(userRequest);
    }

}