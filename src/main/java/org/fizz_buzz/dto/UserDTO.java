package org.fizz_buzz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(@NotBlank(message = "Login must not be blank")
                      @Size(min = 5, max = 15)
                      String login,
                      @NotBlank(message = "Password must not be blank")
                      @Size(min = 8)
                      String password) {
}
