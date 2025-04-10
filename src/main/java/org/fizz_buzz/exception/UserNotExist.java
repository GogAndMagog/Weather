package org.fizz_buzz.exception;

public class UserNotExist extends RuntimeException {

    private static final String USER_NOT_EXIST = "User does not exist";

    public UserNotExist() {
        super(USER_NOT_EXIST);
    }

}