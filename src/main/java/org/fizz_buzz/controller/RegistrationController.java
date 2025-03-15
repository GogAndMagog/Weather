package org.fizz_buzz.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.fizz_buzz.dto.UserRegistrationDTO;
import org.fizz_buzz.exception.ConfirmPasswordException;
import org.fizz_buzz.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final static String COOKIE_SESSION_ID = "SessionId";
    private final static String REGISTRATION_VIEW = "registration";

    private final AuthenticationService authenticationService;

    public RegistrationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public String getRegistrationForm() {
        return REGISTRATION_VIEW;
    }

    @PostMapping
    public String register(@Valid @ModelAttribute("credentials") UserRegistrationDTO params,
                           BindingResult bindingResult,
                           HttpServletResponse response,
                           Model model) {

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

            return REGISTRATION_VIEW;
        } else {
            try {
                var sessionId = authenticationService.registerUser(params.login(), params.password(), params.confirmPassword());
                response.addCookie(new Cookie(COOKIE_SESSION_ID, sessionId.toString()));

                return "redirect:weather";
            }
            catch (ConfirmPasswordException e) {
                model.addAttribute("confirmPassword", e.getMessage());
                return REGISTRATION_VIEW;
            }
        }
    }

}
