package org.programmers.staybb.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.programmers.staybb.global.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundHandler(EntityNotFoundException e) {
        log.error("handleEntityNotfoundException", e);
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE,
            e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OverCrowdingException.class)
    public ResponseEntity<ErrorResponse> overCrowdingHandler(OverCrowdingException e) {
        log.error("OverCrowdingException", e);
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalAccessException.class)
    protected ResponseEntity<ErrorResponse> illegalAccessExceptionHandler(
        IllegalAccessException e) {
        log.error("IllegalAccessException", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.IllegalAccessException);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchFieldException.class)
    protected ResponseEntity<ErrorResponse> noSuchFieldException(NoSuchFieldException e) {
        log.error("NoSuchFieldException", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NoSuchFieldException);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}