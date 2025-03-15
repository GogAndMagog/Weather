package org.fizz_buzz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDTO(@NotBlank(message = "Login must not be blank")
                      String login,
                      @NotBlank(message = "Password must not be blank")
//                      @Pattern(regexp = "^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&? \"]).*$", message = "Incorrect format")
                      String password) {
}
