package org.programmers.staybb.service.user;

import javassist.NotFoundException;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.user.UserRequest;
import org.programmers.staybb.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long addUser(final UserRequest userRequest) {
        User user = userRequest.toEntity();
        return userRepository.save(user).getId();
    }

    public Long removeUser(final Long id) throws NotFoundException {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("ddd"));
        user.deletedUser();
        return user.getId();
    }

}
