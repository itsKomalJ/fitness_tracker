package com.example.fitness_tracker.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception{

    private final HttpStatus httpStatusCode;

    public ApiException(HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public ApiException(String message, HttpStatus httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;

    }

    public ApiException(String message, Throwable cause, HttpStatus httpStatusCode) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

}
