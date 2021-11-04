package org.programmers.staybb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.validation.Valid;
import org.programmers.staybb.dto.room.RoomDetailResponse;
import org.programmers.staybb.dto.room.RoomIdResponse;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.dto.room.RoomSummaryResponse;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/rooms")
@RestController
public class RoomController {

    private final RoomService roomService;

    private RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomIdResponse> save(final @RequestBody @Valid RoomRequest roomRequest)
        throws EntityNotFoundException {
        return ResponseEntity.ok(roomService.save(roomRequest));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<RoomIdResponse> delete(final @PathVariable Long roomId)
        throws EntityNotFoundException {
        return ResponseEntity.ok(roomService.delete(roomId));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDetailResponse> find(final @PathVariable Long roomId)
        throws EntityNotFoundException {
        return ResponseEntity.ok(roomService.find(roomId));
    }

    @GetMapping
    public ResponseEntity<Page<RoomSummaryResponse>> findAll(
        final @RequestParam("hostId") Long hostId,
        Pageable pageable) {
        return ResponseEntity.ok(
            roomService.findAllByHostId(hostId, pageable)
        );
    }

    @PatchMapping("/{roomId}")
    public ResponseEntity<RoomIdResponse> updateSingleField(
        final @PathVariable("roomId") Long roomId,
        final @RequestParam("field") String value,
        final @RequestBody String updateInfo)
        throws NoSuchFieldException, IllegalAccessException, EntityNotFoundException, JsonProcessingException {
        return ResponseEntity.ok(roomService.updateSingleField(roomId, value, updateInfo));
    }

}