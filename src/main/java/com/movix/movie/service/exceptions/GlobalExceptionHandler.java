package com.movix.movie.service.exceptions;

import com.movix.movie.service.responses.ApiSubError;
import com.movix.movie.service.responses.GenericErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        GenericErrorResponse errors = new GenericErrorResponse();
        errors.setStatus(HttpStatus.BAD_REQUEST);
        errors.setMessage("All fields do not satisfy the required parameters structure");
        errors.setSubErrors(new ArrayList<>());
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach(err->{
//            LOGGER.debug(err.toString());
            String fieldName = ((FieldError)err).getField();
            Object rejectedValue =  ((FieldError)err).getRejectedValue();
            String message = err.getDefaultMessage();
            errors.getSubErrors().add(
                    ApiSubError.builder()
                            .message(message)
                            .field(fieldName)
                            .rejectedValue(rejectedValue)
                            .build()
            );
        });
        errors.setTimestamp(new Date());
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> notFoundException(ResourceNotFoundException exception) {
        String message = exception.getMessage();
        GenericErrorResponse errorResponse = GenericErrorResponse.builder()
                .message(message)
                .status(HttpStatus.NOT_FOUND)
                .timestamp(new Date())
                .build();
        return new ResponseEntity<GenericErrorResponse>(errorResponse, errorResponse.getStatus());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<GenericErrorResponse> duplicateEntryException(DuplicateEntryException exception) {
        GenericErrorResponse errorResponse = GenericErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GenericErrorResponse> dataIntegrityViolationException(DataIntegrityViolationException exception) {
        LOGGER.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(GenericErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT)
                .timestamp(new Date())
                .build()
        );
    }
}
