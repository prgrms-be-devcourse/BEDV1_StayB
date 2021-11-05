package org.programmers.staybb.global.exception;

import lombok.Getter;

public class OverCrowdingException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public OverCrowdingException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
