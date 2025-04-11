package org.fizz_buzz.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component(value = "exceptionHandlerFilter")
public class ExceptionHandlerFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        try{
            chain.doFilter(req, res);
        }
        catch(Exception e){
            req.setAttribute("exception", e);
            req.getServletContext().getRequestDispatcher("/filterError").forward(req, res);
        }
    }

}