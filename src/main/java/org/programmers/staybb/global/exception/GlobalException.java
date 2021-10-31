package org.programmers.staybb.global.exception;

import javassist.NotFoundException;
import org.programmers.staybb.global.response.ApiResponse;
import org.programmers.staybb.global.response.ApiUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse notFoundHandler(NotFoundException e) {
        return ApiUtils.error(e.getMessage(), 404);
    }

}
