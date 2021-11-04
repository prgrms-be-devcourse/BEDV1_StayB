package org.programmers.staybb.global.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.programmers.staybb.global.exception.ErrorCode;
import org.springframework.validation.BindingResult;

@Getter
public class ErrorResponse {

    private String message;
    private int status;
    @Builder.Default
    private List<FieldError> errors = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    protected ErrorResponse() {
    }

    @Builder
    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.errors = errors;
        this.serverDatetime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return ErrorResponse.builder().code(errorCode).build();
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return ErrorResponse.builder().code(code).errors(FieldError.of(bindingResult)).build();
    }

    @Getter
    public static class FieldError {

        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.parallelStream()
                .map(error -> new FieldError(
                    error.getField(),
                    error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                    error.getDefaultMessage()))
                .collect(Collectors.toList());
        }
    }

}
