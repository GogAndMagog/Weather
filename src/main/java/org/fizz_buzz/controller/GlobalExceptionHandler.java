package org.fizz_buzz.controller;

import org.fizz_buzz.exception.WrongCredentialsException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class, produces = MediaType.TEXT_HTML_VALUE)
    public String handleAllExceptions(Exception ex) {
        return "error";
    }

}
