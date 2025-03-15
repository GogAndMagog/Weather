package org.fizz_buzz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRegistrationDTO(@NotBlank(message = "Login must not be blank")
                                  String login,
//                                  @Pattern(regexp = "^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&? \"]).*$", message = "Incorrect format")
                                  @NotBlank(message = "Password must not be blank")
                                  String password,
                                  @NotBlank(message = "Confirm password must not be blank")
                                  String confirmPassword) {
}
