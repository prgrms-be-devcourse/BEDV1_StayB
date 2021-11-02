package org.programmers.staybb.controller.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.user.UserRequest;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.UserRepository;
import org.programmers.staybb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
class HostControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    private Long userId;

    @Autowired
    HostRepository hostRepository;

    @BeforeEach
    void setup() {
        UserRequest userRequest = UserRequest.builder()
            .email("sunho00@gmail.com")
            .phoneNumber("010-5535-5555")
            .name("김선호")
            .birthday(LocalDate.of(1996, 7, 20))
            .bio("안녕하세요")
            .build();

        userId = userService.addUser(userRequest);
    }


    @Test
    @DisplayName("Host add 테스트")
    void saveUser() throws Exception {
        mockMvc.perform(post("/v1/host/add/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // 200
            .andDo(print());
    }

    @Test
    @DisplayName("Host 프로필 조회 테스트")
    void showHost() throws Exception {
        Long hostID = getHost().getId();
        mockMvc.perform(get("/v1/host/{hostId}", hostID)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // 200
            .andDo(print());
    }

    private Host getHost() {
        User user = userRepository.findById(userId).get();
        return hostRepository.save(Host.builder().user(user).build());
    }


}