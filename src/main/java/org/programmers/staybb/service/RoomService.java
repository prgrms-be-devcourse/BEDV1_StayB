package org.programmers.staybb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import javax.persistence.Embedded;
import org.json.JSONObject;
import org.programmers.staybb.domain.room.Room;
import org.programmers.staybb.domain.user.Host;
import org.programmers.staybb.dto.room.RoomDetailResponse;
import org.programmers.staybb.dto.room.RoomIdResponse;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.dto.room.RoomSummaryResponse;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.global.exception.ErrorCode;
import org.programmers.staybb.repository.HostRepository;
import org.programmers.staybb.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HostRepository hostRepository;
    private final ObjectMapper objectMapper;

    public RoomService(RoomRepository roomRepository, HostRepository hostRepository,
        ObjectMapper objectMapper) {
        this.roomRepository = roomRepository;
        this.hostRepository = hostRepository;
        this.objectMapper = objectMapper;
    }

    public RoomIdResponse save(final RoomRequest roomRequest) throws EntityNotFoundException {
        Host host = hostRepository.findById(roomRequest.getHostId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.HOST_NOT_FOUND));
        return new RoomIdResponse(roomRepository.save(roomRequest.toEntity(host)).getId());
    }

    public RoomIdResponse delete(final Long roomId) throws EntityNotFoundException {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ROOM_NOT_FOUND));
        roomRepository.delete(room);
        return new RoomIdResponse(room.getId());
    }

    @Transactional(readOnly = true)
    public RoomDetailResponse find(final Long roomId) throws EntityNotFoundException {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ROOM_NOT_FOUND));
        return RoomDetailResponse.of(room);
    }

    @Transactional(readOnly = true)
    public Page<RoomSummaryResponse> findAllByHostId(final Long hostId, final Pageable pageable) {
        Page<Room> roomPage = roomRepository.findAllByHostId(hostId, pageable);
        return roomPage.map(RoomSummaryResponse::of);
    }

    public RoomIdResponse updateSingleField(final Long roomId, final String field,
        final String value)
        throws EntityNotFoundException, NoSuchFieldException, IllegalAccessException, JsonProcessingException {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ROOM_NOT_FOUND));

        Field fieldToUpdate = room.getClass().getDeclaredField(field);

        if (fieldToUpdate.isAnnotationPresent(Embedded.class)) {
            room.setField(field, objectMapper.readValue(value, fieldToUpdate.getType()));
        } else {
            JSONObject info = new JSONObject(value);
            room.setField(field, info.get(field));
        }
        return new RoomIdResponse(room.getId());
    }

}