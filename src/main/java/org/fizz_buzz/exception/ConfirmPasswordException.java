package org.fizz_buzz.exception;

public class ConfirmPasswordException extends RuntimeException {

    private static final String MESSAGE = "Confirm Password must be the same as Password";

    public ConfirmPasswordException(){
        super(MESSAGE);
    }
}
