package org.programmers.staybb.service;

import java.util.Map;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.global.exception.ErrorCode;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Long save(final RoomRequest roomRequest) throws EntityNotFoundException {
        Host host = hostRepository.findById(roomRequest.getHostId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.HOST_NOT_FOUND));
        return roomRepository.save(roomRequest.toEntity(host)).getId();
    }

    public Long delete(final Long roomId) throws EntityNotFoundException {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ROOM_NOT_FOUND));
        roomRepository.delete(room);
        return roomId;
    }

    @Transactional(readOnly = true)
    public Room find(final Long roomId) throws EntityNotFoundException {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ROOM_NOT_FOUND));
        return room;
    }

    @Transactional(readOnly = true)
    public Page<Room> findAllByHostId(final Long hostId, final Pageable pageable) {
        Page<Room> roomPage = roomRepository.findAllByHostId(hostId, pageable);
        return roomPage;
    }

    @Transactional
    public Long updateSingleField(Long roomId, Map<String, Object> updateInfo)
        throws NoSuchFieldException, IllegalAccessException, EntityNotFoundException {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ROOM_NOT_FOUND));

        String fieldToChange = updateInfo.keySet().toArray()[0].toString();
        System.out.println(fieldToChange);
        room.setField(fieldToChange, updateInfo.get(fieldToChange));

        return room.getId();
    }
}