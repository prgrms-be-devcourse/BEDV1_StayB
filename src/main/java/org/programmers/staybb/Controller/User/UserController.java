package org.programmers.staybb.Controller.User;

import javassist.NotFoundException;
import javax.validation.Valid;
import org.programmers.staybb.dto.user.UserRequest;
import org.programmers.staybb.global.ApiResponse;
import org.programmers.staybb.global.ApiUtils;
import org.programmers.staybb.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
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
    public ApiResponse<Long> SignUp(final @RequestBody @Valid UserRequest userRequest) {
        return ApiUtils.success(userService.addUser(userRequest));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Long> removeUser(final @PathVariable Long id) throws NotFoundException {
        return ApiUtils.success(userService.removeUser(id));
    }

}
