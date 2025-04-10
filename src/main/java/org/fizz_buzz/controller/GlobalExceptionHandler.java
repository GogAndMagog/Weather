package org.fizz_buzz.controller;

import org.fizz_buzz.util.ApplicationConstant;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String handleAllExceptions(Exception ex) {
        return ApplicationConstant.ERROR_VIEW;
    }

}
