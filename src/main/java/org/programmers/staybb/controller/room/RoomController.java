package org.programmers.staybb.controller.room;

import javassist.NotFoundException;
import org.programmers.staybb.dto.room.RoomRequestDto;
import org.programmers.staybb.dto.room.RoomResponseDto;
import org.programmers.staybb.global.ApiResponse;
import org.programmers.staybb.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

  private final RoomService roomService;

  @Autowired
  private RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @ExceptionHandler
  public ApiResponse<String> notFoundHandler(NotFoundException e) {
    return ApiResponse.fail(404, e.getMessage());
  }

  @PostMapping("/rooms")
  public ApiResponse<RoomResponseDto> save(@RequestBody RoomRequestDto roomDto) {
    return ApiResponse.ok(roomService.save(roomDto));
  }

  @DeleteMapping("/rooms/{roomId}")
  public ApiResponse<String> delete(@PathVariable int roomId) throws NotFoundException {
    roomService.delete(roomId);
    return ApiResponse.ok("숙소가 삭제되었습니다.");
  }

}
