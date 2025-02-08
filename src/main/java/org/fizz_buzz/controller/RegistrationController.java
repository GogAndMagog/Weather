package org.fizz_buzz.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.SessionRepository;
import org.fizz_buzz.repository.UserRepository;
import org.fizz_buzz.service.AuthenticationAndAuthorizationService;
import org.fizz_buzz.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.View;
import javax.swing.text.html.HTML;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final static String COOKIE_SESSION_ID = "SessionId";
    private final static String USER_REGISTERED = "User registered";
    private final static String USER_NOT_REGISTERED = "Uer not registered";
    private final static String MISSED_PARAMETERS_ERROR = "Parameters \"login\" and \"password\" must be filled";
    private final RegistrationService registrationService;
    private final AuthenticationAndAuthorizationService authenticationAndAuthorizationService;

    public RegistrationController(RegistrationService registrationService,
                                  AuthenticationAndAuthorizationService authenticationAndAuthorizationService) {
        this.registrationService = registrationService;
        this.authenticationAndAuthorizationService = authenticationAndAuthorizationService;
    }

    @GetMapping
    public ModelAndView getRegistrationForm() {
        return new ModelAndView("registration");
    }

//    @GetMapping
    public void checkRegister(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        var cookies = request.getCookies();

        if (cookies != null) {
            var sessionId = Arrays.stream(cookies)
                    .filter(cookie ->
                            cookie.getName().equals(COOKIE_SESSION_ID)
                            && !cookie.getValue().isEmpty())
                    .findAny()
                    .map(Cookie::getValue)
                    .map(UUID::fromString);

            if (sessionId.isPresent()) {
                if (authenticationAndAuthorizationService.isAuthorized(sessionId.get())){
                    response.setStatus(HttpServletResponse.SC_FOUND);
                    response.getWriter().write(USER_REGISTERED);
                } else {
//                    cookies[0].setValue(null);
                    response.addCookie(new Cookie(COOKIE_SESSION_ID, null));
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(USER_NOT_REGISTERED);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(USER_NOT_REGISTERED);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(USER_NOT_REGISTERED);
        }
    }

    @PostMapping
    public void register(@RequestParam("login") String login,
                         @RequestParam("password") String password,
                         HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(MISSED_PARAMETERS_ERROR);
        } else {
            var sessionId = registrationService.registerUser(login, password);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(USER_REGISTERED);
            response.addCookie(new Cookie(COOKIE_SESSION_ID, sessionId.toString()));
        }
    }

}
