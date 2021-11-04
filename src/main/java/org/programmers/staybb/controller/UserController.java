package org.programmers.staybb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.validation.Valid;
import org.programmers.staybb.dto.user.UserIdResponse;
import org.programmers.staybb.dto.user.UserRequest;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserIdResponse> createUser(
        final @RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserIdResponse> deleteUser(final @PathVariable Long id)
        throws EntityNotFoundException {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserIdResponse> updateSingleField(
        final @PathVariable("id") Long id,
        final @RequestParam("field") String field,
        final @RequestBody String value)
        throws NoSuchFieldException, JsonProcessingException, IllegalAccessException {
        return ResponseEntity.ok(userService.updateSingleField(id, field, value));
    }

}

