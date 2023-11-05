package com.halilkrkn.usersapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetails handleResourceMethodNotSupportedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        return ProblemDetails.builder()
                .message(httpRequestMethodNotSupportedException.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetails handleResourceMessageNotReadableBadRequestException(HttpMessageNotReadableException httpMessageNotReadableException) {
        return ProblemDetails.builder()
                .message(httpMessageNotReadableException.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetails handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return ProblemDetails.builder()
                .message(resourceNotFoundException.getMessage())
                .build();
    }
}
