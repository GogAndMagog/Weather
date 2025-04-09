package org.fizz_buzz.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(@NotBlank(message = "login must not be blank")
                                  @Size(min = 5, max = 15)
                                  String login,
//                                  @Pattern(regexp = "^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&? \"]).*$", message = "Incorrect format")
                                  @NotBlank(message = "password must not be blank")
                                  @Size(min = 8)
                                  String password,
                                  @Size(min = 8)
                                  @NotBlank(message = "confirm password must not be blank")
                                  String confirmPassword) {

    @AssertTrue(message = "password and confirmed password must be equal")
    private boolean isPasswordMatches(){
        return password.equals(confirmPassword);
    }
}
