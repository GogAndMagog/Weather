package org.fizz_buzz.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.fizz_buzz.dto.UserDTO;
import org.fizz_buzz.exception.UserNotExist;
import org.fizz_buzz.exception.WrongCredentialsException;
import org.fizz_buzz.service.AuthenticationService;
import org.fizz_buzz.util.ApplicationConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthenticationController {

    private final static String COOKIE_SESSION_ID = "SessionId";

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/authenticate")
    public String getView(@CookieValue(value = COOKIE_SESSION_ID, required = false) UUID sessionId,
                          Model model) {

        if (sessionId != null && authenticationService.authenticate(sessionId)) {
            return "redirect:weather";
        }

        model.addAttribute("credentials", new UserDTO("", ""));
        model.addAttribute("method","GET");

        return ApplicationConstant.AUTHENTICATION_VIEW;
    }

    @PostMapping("/authenticate")
    @Validated
    public String authenticate(@Valid @ModelAttribute("credentials") UserDTO params,
                               BindingResult bindingResult,
                               Model model,
                               HttpServletResponse response) throws IOException {

        if (bindingResult != null && bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                    .forEach(error -> {
                        var field = ((FieldError) error).getField() + "Error";

                        var messages = (String) model.getAttribute(field);
                        if (messages == null) {
                            messages = error.getDefaultMessage();
                        } else {
                            messages += "\n " + error.getDefaultMessage();
                        }

                        model.addAttribute(field, messages);
                    });

            return ApplicationConstant.AUTHENTICATION_VIEW;
        } else {

            try {
                var sessionId = authenticationService.createSession(params.login(), params.password());
                response.addCookie(new Cookie(COOKIE_SESSION_ID, sessionId.toString()));
            } catch (WrongCredentialsException | UserNotExist e) {
                model.addAttribute("globalErrors", e.getMessage());
                model.addAttribute("method", "GET");

                return ApplicationConstant.AUTHENTICATION_VIEW;
            }

            return "redirect:weather";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {

        var nullCookie = new Cookie(COOKIE_SESSION_ID, "");
        //needed to delete cookie on client
        nullCookie.setMaxAge(0);

        response.addCookie(nullCookie);
        return "redirect:authenticate";
    }
}
