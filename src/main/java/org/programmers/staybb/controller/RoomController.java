package org.programmers.staybb.controller;

import javassist.NotFoundException;
import javax.validation.Valid;
import org.programmers.staybb.dto.room.RoomRequest;
import org.programmers.staybb.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/rooms")
@RestController
public class RoomController {

    private final RoomService roomService;

    private RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<Long> save(final @RequestBody @Valid RoomRequest roomRequest)
        throws NotFoundException {
        return ResponseEntity.ok(roomService.save(roomRequest));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Long> delete(final @PathVariable Long roomId) throws NotFoundException {
        return ResponseEntity.ok(roomService.delete(roomId));
    }

}