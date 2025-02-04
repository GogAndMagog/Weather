package org.fizz_buzz.exception;

public class IncorrectPassword extends RuntimeException {
    private static final String PASSWORD_NOT_FOUND = "Password not found";

    public IncorrectPassword() {
        super(PASSWORD_NOT_FOUND);
    }
}
