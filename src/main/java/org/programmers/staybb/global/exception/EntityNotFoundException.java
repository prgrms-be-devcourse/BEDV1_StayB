package org.programmers.staybb.global.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
