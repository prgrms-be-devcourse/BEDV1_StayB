package org.programmers.staybb.controller;

import javassist.NotFoundException;
import org.programmers.staybb.dto.room.RoomRequestDto;
import org.programmers.staybb.global.response.ApiResponse;
import org.programmers.staybb.global.response.ApiUtils;
import org.programmers.staybb.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ApiResponse<Long> save(@RequestBody RoomRequestDto roomDto) {
        return ApiUtils.success(roomService.save(roomDto));
    }

    @DeleteMapping("/{roomId}")
    public ApiResponse<Long> delete(@PathVariable int roomId) throws NotFoundException {
        return ApiUtils.success(roomService.delete(roomId));
    }

}
