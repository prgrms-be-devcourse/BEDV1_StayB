package org.programmers.staybb.setup;

import java.time.LocalDate;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.user.UserRequest;
import org.programmers.staybb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSetup {

    @Autowired
    private UserRepository userRepository;

    public User saveUser() {
        return userRepository.save(buildUserRequest().toEntity());
    }

    public UserRequest buildUserRequest() {
        return UserRequest.builder()
            .bio("Hi~")
            .birthday(LocalDate.of(1996, 7, 20))
            .name("김선호")
            .phoneNumber("010-3232-3232")
            .email("susus@gmail.com")
            .build();
    }

}