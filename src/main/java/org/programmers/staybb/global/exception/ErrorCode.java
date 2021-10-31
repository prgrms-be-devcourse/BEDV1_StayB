package org.programmers.staybb.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(404, "해당 사용자를 찾을 수 없습니다."),
    HOST_NOT_FOUND(404, "해당 호스트를 찾을 수 없습니다."),
    ROOM_NOT_FOUND(404, "해당 숙소를 찾을 수 없습니다."),
    INVALID_INPUT_VALUE(400, "입력값을 다시 확인해주세요.");

    private final int status;

    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
