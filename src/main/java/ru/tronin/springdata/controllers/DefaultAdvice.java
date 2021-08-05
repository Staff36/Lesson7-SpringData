package ru.tronin.springdata.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tronin.springdata.exceptions.NoEntityException;

@Slf4j
@ControllerAdvice
public class DefaultAdvice extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(NoEntityException.class)
    public ResponseEntity<?> handleNoEntityException(NoEntityException e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
