package com.chrisnkl.libs.domain;

import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;

public record ApiResponse<T>(
        HttpStatus status,
        int statusCode,
        String message,
        T data,
        Instant timestamp
) {

    public ApiResponse {
        if (status == null) {
            status = HttpStatus.OK;
        }
        if (statusCode == 0) {
            statusCode = status.value();
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }

    public ApiResponse(HttpStatus status, String message, T data) {
        this(status, status != null ? status.value() : 200, message, data, Instant.now());
    }

    public ApiResponse(HttpStatus status, String message) {
        this(status, message, null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK, "Success", data);
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK, message, data);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(HttpStatus.CREATED, message, data);
    }

    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, message, null);
    }

    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(HttpStatus.NOT_FOUND, message, null);
    }

    public static <T> ApiResponse<T> internalError(String message) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return new ApiResponse<>(status, message, null);
    }

    public HttpResponseMessage toHttpResponse(HttpRequestMessage<?> request) {
        return request.createResponseBuilder(this.status)
                .header("Content-Type", "application/json")
                .body(this)
                .build();
    }

    public HttpResponseMessage toHttpResponse(HttpRequestMessage<?> request, ObjectMapper objectMapper) {
        try {
            return request.createResponseBuilder(this.status)
                    .header("Content-Type", "application/json")
                    .body(objectMapper.writeValueAsString(this))
                    .build();
        } catch (JacksonException e) {
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body("{\"status\":\"INTERNAL_SERVER_ERROR\",\"statusCode\":500,\"message\":\"Failed to serialize response body\"}")
                    .build();
        }
    }

}

