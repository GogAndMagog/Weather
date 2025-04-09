package org.fizz_buzz.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.fizz_buzz.dto.UserRegistrationDTO;
import org.fizz_buzz.exception.ConfirmPasswordException;
import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.service.AuthenticationService;
import org.fizz_buzz.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final static String COOKIE_SESSION_ID = "SessionId";
    private final static String REDIRECT = "redirect:";
    private final static String REGISTRATION_VIEW = "registration";
    private final static String WEATHER_VIEW = "weather";

    private final AuthenticationService authenticationService;
    private final SessionService sessionService;

    public RegistrationController(AuthenticationService authenticationService,
                                  SessionService sessionService) {
        this.authenticationService = authenticationService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String getRegistrationForm(Model model) {

        // needed for correct render Thymeleaf template, model attribute must be always exists for th:field
        model.addAttribute("credentials", new UserRegistrationDTO("", "", ""));
        // needed for correct displaying input-fields of the credentials form
        model.addAttribute("method","GET");

        return REGISTRATION_VIEW;
    }

    @PostMapping
    public String register(@Valid @ModelAttribute("credentials") UserRegistrationDTO params,
                           BindingResult bindingResult,
                           HttpServletResponse response,
                           Model model) {

        if (bindingResult.hasErrors()) {
            // because #fields.hasErrors() doesn't works correct
            // for @AssertTrue validation annotation we need this workaround
            model.addAttribute("isPasswordMatches", bindingResult.hasFieldErrors("passwordMatches"));

            return REGISTRATION_VIEW;
        } else {
            try {
                var sessionId = authenticationService.registerUser(params.login(), params.password(), params.confirmPassword());
                response.addCookie(new Cookie(COOKIE_SESSION_ID, sessionId.toString()));

                return REDIRECT + WEATHER_VIEW;
            } catch (ConfirmPasswordException e) {
                model.addAttribute("confirmPassword", e.getMessage());
                return REGISTRATION_VIEW;
            }
        }
    }
}
