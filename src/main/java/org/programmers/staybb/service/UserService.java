package org.programmers.staybb.service;

import org.json.JSONObject;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.user.UserIdResponse;
import org.programmers.staybb.dto.user.UserRequest;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.global.exception.ErrorCode;
import org.programmers.staybb.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserIdResponse createUser(final UserRequest userRequest) {
        User user = userRequest.toEntity();
        return new UserIdResponse(userRepository.save(user).getId());
    }

    public UserIdResponse deleteUser(final Long userId) throws EntityNotFoundException {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
        return new UserIdResponse(userId);
    }

    public UserIdResponse updateSingleField(final Long id, final String field, final String value)
        throws EntityNotFoundException, NoSuchFieldException, IllegalAccessException {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));

        JSONObject info = new JSONObject(value);
        user.setField(field, info.get(field));

        return new UserIdResponse(user.getId());
    }

}
