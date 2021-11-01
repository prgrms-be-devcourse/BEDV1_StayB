package org.programmers.staybb.service;

import javassist.NotFoundException;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final HostRepository hostRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, HostRepository hostRepository) {
        this.roomRepository = roomRepository;
        this.hostRepository = hostRepository;
    }

    public Long save(final RoomRequest roomRequest) throws NotFoundException{
        Host host = hostRepository.findById(roomRequest.getHostId())
            .orElseThrow(() -> new NotFoundException("해당 host 정보가 없습니다."));
        return roomRepository.save(roomRequest.toEntity(host)).getId();
    }

    public Long delete(final Long roomId) throws NotFoundException {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new NotFoundException("해당 숙소 정보가 없습니다."));
        roomRepository.delete(room);
        return roomId;
    }
}