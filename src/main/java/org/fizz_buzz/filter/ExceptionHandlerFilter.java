package org.fizz_buzz.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.io.IOException;

@Component(value = "exceptionHandlerFilter")
public class ExceptionHandlerFilter extends HttpFilter {

    @Autowired
    private ExceptionHandlerExceptionResolver  exceptionHandlerExceptionResolver;

    public ExceptionHandlerFilter(ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver) {

        this.exceptionHandlerExceptionResolver = exceptionHandlerExceptionResolver;
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        try{
            chain.doFilter(req, res);
        }
        catch(Exception e){
            exceptionHandlerExceptionResolver.resolveException(req, res, null, e);
        }
    }
}
