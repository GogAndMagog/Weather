package org.fizz_buzz.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.util.ApplicationConstant;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String handleAllExceptions(Exception ex, HttpServletResponse response) {

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return ApplicationConstant.ERROR_VIEW;
    }

}
