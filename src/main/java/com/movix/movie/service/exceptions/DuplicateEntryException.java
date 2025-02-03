package com.movix.movie.service.exceptions;

public class DuplicateEntryException extends Exception {
    String resource;
    String field;
    String rejectedValue;

    public DuplicateEntryException(String resourceName, String parameterName, String parameterValue) {
        super(String.format("%s is already present for %s : %s", resourceName, parameterName, parameterValue));
        this.resource = resourceName;
        this.field = parameterName;
        this.rejectedValue = parameterValue;
    }
}
