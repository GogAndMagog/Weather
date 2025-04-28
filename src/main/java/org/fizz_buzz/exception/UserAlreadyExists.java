package org.fizz_buzz.exception;

public class UserAlreadyExists extends RuntimeException {

    private static final String USER_NOT_EXIST = "User %s already exist";

    public UserAlreadyExists(String userName) {
        super(USER_NOT_EXIST.formatted(userName));
    }

}