package org.programmers.staybb.global;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
public class ApiResponse<T> {

    private final ApiError apiError;
    private final T data;
    private final boolean success;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime serverDatetime;

    public ApiResponse(T data, boolean success, ApiError apiError) {
        this.apiError = apiError;
        this.data = data;
        this.success = success;
        this.serverDatetime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }


}
