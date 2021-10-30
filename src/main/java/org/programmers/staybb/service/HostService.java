package org.programmers.staybb.service;

import java.util.List;
import java.util.stream.Collectors;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.user.HostResponse;
import org.programmers.staybb.global.exception.ApiException;
import org.programmers.staybb.global.exception.ErrorCode;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HostService {

    private final UserRepository userRepository;

    private final HostRepository hostRepository;

    public HostService(UserRepository userRepository,
        HostRepository hostRepository) {
        this.userRepository = userRepository;
        this.hostRepository = hostRepository;
    }

    public Long addHost(final Long userId) {
        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        Host host = Host.builder()
            .user(findUser)
            .build();
        return host.getId();
    }

    @Transactional(readOnly = true)
    public HostResponse findHost(final Long hostId) {
        Host findHost = hostRepository.findById(hostId)
            .orElseThrow(() -> new ApiException(ErrorCode.HOST_NOT_FOUND));

        List<Long> roomsId = findHost.getRooms().stream().map(Room::getId)
            .collect(Collectors.toList());

        return HostResponse.builder()
            .name(findHost.getUser().getName())
            .is_superhost(findHost.is_superHost())
            .roomsId(roomsId).build();
    }

}
