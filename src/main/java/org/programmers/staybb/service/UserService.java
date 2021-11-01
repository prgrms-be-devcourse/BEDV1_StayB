package org.programmers.staybb.service;

import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.user.UserRequest;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.global.exception.ErrorCode;
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

    public Long removeUser(final Long id) throws EntityNotFoundException {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
        return user.getId();
    }
}
