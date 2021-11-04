package org.programmers.staybb.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(404, "해당 사용자를 찾을 수 없습니다."),
    HOST_NOT_FOUND(404, "해당 호스트를 찾을 수 없습니다."),
    ROOM_NOT_FOUND(404, "해당 숙소를 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND(404, "해당 예약을 찾을 수 없습니다."),
    INVALID_INPUT_VALUE(400, "입력값을 다시 확인해주세요."),
    IllegalAccessException(400, "해당 정보를 수정할 권한이 없습니다."),
    NoSuchFieldException(400, "유효하지 않은 필드는 수정할 수 없습니다."),
    OVER_CROWDING(400, "게스트 인원이 초과했습니다.");

    private final int status;

    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
