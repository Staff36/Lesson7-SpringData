package ru.tronin.springdata.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tronin.springdata.exceptions.NoEntityException;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler(NoEntityException.class)
    public String handleNoEntityException(NoEntityException e){
        return "products/notFound";
    }
}
