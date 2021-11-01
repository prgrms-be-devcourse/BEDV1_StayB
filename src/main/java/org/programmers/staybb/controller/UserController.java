package org.programmers.staybb.controller;

import javax.validation.Valid;
import org.programmers.staybb.dto.user.UserRequest;
import org.programmers.staybb.global.exception.EntityNotFoundException;
import org.programmers.staybb.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Long> signUp(final @RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.addUser(userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> removeUser(final @PathVariable Long id)
        throws EntityNotFoundException {
        return ResponseEntity.ok().body(userService.removeUser(id));
    }

}

