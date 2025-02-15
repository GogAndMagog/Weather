package org.fizz_buzz.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class AuthenticationController {

    private final static String COOKIE_SESSION_ID = "SessionId";

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/authenticate")
    public ModelAndView getView(){
        return new ModelAndView("authentication");
    }

    @PostMapping("/authenticate")
    public void authenticate(@RequestParam("login") String login,
                             @RequestParam("password") String password,
                             HttpServletResponse response) throws IOException {
        var sessionId = authenticationService.createSession(login, password);
        response.addCookie(new Cookie(COOKIE_SESSION_ID, sessionId.toString()));
        response.sendRedirect("dummy") ;
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        response.addCookie(new Cookie(COOKIE_SESSION_ID,""));
    }
}
