package org.programmers.staybb.service;

import java.util.List;
import java.util.stream.Collectors;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.domain.user.User;
import org.programmers.staybb.dto.user.HostFindResponse;
import org.programmers.staybb.dto.user.HostIdResponse;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.global.exception.ErrorCode;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class HostService {

    private final UserRepository userRepository;
    private final HostRepository hostRepository;

    public HostService(UserRepository userRepository,
        HostRepository hostRepository) {
        this.userRepository = userRepository;
        this.hostRepository = hostRepository;
    }

    public HostIdResponse addHost(final Long userId) {
        User findUser = validUserId(userId);
        Host host = Host.builder()
            .user(findUser)
            .build();
        return new HostIdResponse(hostRepository.save(host).getId());
    }

    @Transactional(readOnly = true)
    public HostFindResponse findHost(final Long hostId) {
        Host findHost = validHostId(hostId);

        List<Long> roomIds = findHost.getRooms().stream().map(Room::getId)
            .collect(Collectors.toList());

        return HostFindResponse.of(findHost, findHost.getUser().getName(), roomIds);
    }

    public Long changeToHost(final Long userId) {
        validUserId(userId);
        return hostRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.HOST_NOT_FOUND))
            .getId();
    }

    public Long changeToUser(final Long hostID) {
        Host host = validHostId(hostID);
        return host.getUser().getId();
    }

    private User validUserId(Long userId) throws EntityNotFoundException {
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private Host validHostId(Long hostId) throws EntityNotFoundException {
        return hostRepository.findById(hostId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.HOST_NOT_FOUND));
    }

}
