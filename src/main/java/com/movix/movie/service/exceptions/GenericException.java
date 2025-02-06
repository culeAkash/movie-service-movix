package com.movix.movie.service.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;


@Builder
@Getter
public class GenericException extends RuntimeException {
    private final String message;
    private final HttpStatusCode statusCode;

    public GenericException(String message, HttpStatusCode statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }
}
