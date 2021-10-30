package org.programmers.staybb.global;

public class ApiUtils {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>( data, true, null);
    }

    public static ApiResponse<?> error(String message, int statusCode) {
        return new ApiResponse<>(null,false,  new ApiError(message, statusCode));
    }
}
