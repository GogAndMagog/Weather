package org.fizz_buzz.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.fizz_buzz.dto.UserDTO;
import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.service.AuthenticationService;
import org.fizz_buzz.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthenticationController {

    private final static String COOKIE_SESSION_ID = "SessionId";
    private final static String AUTHENTICATION_VIEW = "authentication";

    private final AuthenticationService authenticationService;
    private final SessionService sessionService;

    public AuthenticationController(AuthenticationService authenticationService,
                                    SessionService sessionService) {
        this.authenticationService = authenticationService;
        this.sessionService = sessionService;
    }

    @GetMapping("/authenticate")
    public ModelAndView getView(Model model) {

        return new ModelAndView(AUTHENTICATION_VIEW);
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

            return AUTHENTICATION_VIEW;
        } else {
            var sessionId = authenticationService.createSession(params.login(), params.password());
            response.addCookie(new Cookie(COOKIE_SESSION_ID, sessionId.toString()));

            return "redirect:weather";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        response.addCookie(new Cookie(COOKIE_SESSION_ID, ""));
        return "redirect:authenticate";
    }
}
