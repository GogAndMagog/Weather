package org.fizz_buzz.exception;

public class WrongCredentialsException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Wrong credentials";

    public WrongCredentialsException() {
        super(ERROR_MESSAGE);
    }

}
