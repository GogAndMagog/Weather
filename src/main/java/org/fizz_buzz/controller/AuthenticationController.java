package org.fizz_buzz.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.fizz_buzz.dto.UserDTO;
import org.fizz_buzz.service.AuthenticationService;
import org.fizz_buzz.util.ApplicationConstant;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class AuthenticationController {

    private final static String COOKIE_SESSION_ID = "SessionId";

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/authenticate")
    public String getView(Model model) {
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
            var sessionId = authenticationService.createSession(params.login(), params.password());
            response.addCookie(new Cookie(COOKIE_SESSION_ID, sessionId.toString()));

            return "redirect:%s".formatted(ApplicationConstant.WEATHER_VIEW);
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        response.addCookie(new Cookie(COOKIE_SESSION_ID, ""));
        return "redirect:%s".formatted(ApplicationConstant.AUTHENTICATION_VIEW);
    }
}
