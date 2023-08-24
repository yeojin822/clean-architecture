package com.example.points.common.exception;

public class ApiException extends RuntimeException {

    public ApiException(String errorMessage) {
        super(errorMessage);
    }

    public ApiException(String errorMessage, Throwable t) {
        super(errorMessage, t);
    }
}
