package org.fizz_buzz.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.util.ApplicationConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilterErrorController {

    @GetMapping("/filterError")
    public String handleError(HttpServletRequest req, HttpServletResponse res) {

        //todo: log this exceptions
        var exception = (Exception) req.getAttribute("exception");
        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return ApplicationConstant.ERROR_VIEW;
    }
}
