package ru.tronin.springdata.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tronin.springdata.exceptions.ErrorResponse;
import ru.tronin.springdata.exceptions.NoEntityException;

import javax.persistence.EntityNotFoundException;


@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNoEntityException(EntityNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
