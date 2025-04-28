package org.fizz_buzz.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.fizz_buzz.dto.UserRegistrationDTO;
import org.fizz_buzz.exception.ConfirmPasswordException;
import org.fizz_buzz.exception.UserAlreadyExists;
import org.fizz_buzz.service.AuthenticationService;
import org.fizz_buzz.util.ApplicationConstant;
import org.mindrot.jbcrypt.BCrypt;
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

    private final AuthenticationService authenticationService;

    public RegistrationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public String getRegistrationForm(@CookieValue(value = COOKIE_SESSION_ID, required = false) UUID sessionId,
                                      Model model) {

        if (sessionId != null && authenticationService.authenticate(sessionId)) {
            return "redirect:weather";
        }

        // needed for correct render Thymeleaf template, model attribute must be always exists for th:field
        model.addAttribute("credentials", new UserRegistrationDTO("", "", ""));
        // needed for correct displaying input-fields of the credentials form
        model.addAttribute("method","GET");

        return ApplicationConstant.REGISTRATION_VIEW;
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

            return ApplicationConstant.REGISTRATION_VIEW;
        } else {
            try {

                var salt = BCrypt.gensalt();
                var hashedPassword = BCrypt.hashpw(params.password(), salt);
                var hashedConfirmPassword = BCrypt.hashpw(params.password(), salt);

                var sessionId = authenticationService.registerUser(params.login(), hashedPassword, hashedConfirmPassword);
                response.addCookie(new Cookie(COOKIE_SESSION_ID, sessionId.toString()));

                return "redirect:weather";
            } catch (ConfirmPasswordException e) {
                model.addAttribute("confirmPassword", e.getMessage());
                return ApplicationConstant.REGISTRATION_VIEW;
            }
            catch (UserAlreadyExists e) {
                model.addAttribute("globalErrors", e.getMessage());
                model.addAttribute("method", "GET");

                return ApplicationConstant.REGISTRATION_VIEW;
            }
        }
    }
}
