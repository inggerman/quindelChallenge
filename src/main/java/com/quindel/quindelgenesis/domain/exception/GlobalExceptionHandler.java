package com.quindel.quindelgenesis.domain.exception;

import com.quindel.quindelgenesis.application.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError> handleNotFoundException(NotFoundException ex) {


        return new ResponseEntity<>(ResponseError
                .builder()
                .errorId(UUID.randomUUID())
                .errorMessage(ex.getErrorMessage())
                .errorCode(ex.getErrorCode())
                .build(), HttpStatus.NOT_FOUND);
    }
}