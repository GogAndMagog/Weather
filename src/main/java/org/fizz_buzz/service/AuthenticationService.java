package org.fizz_buzz.service;

import org.fizz_buzz.exception.ConfirmPasswordException;
import org.fizz_buzz.exception.UserAlreadyExists;
import org.fizz_buzz.exception.WrongCredentialsException;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.SessionRepository;
import org.fizz_buzz.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@PropertySource("classpath:application.properties")
public class AuthenticationService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final UserService userService;
    private final SessionService sessionService;


    @Value("${sessionDurationTime}")
    private long sessionDurationTime;

    public AuthenticationService(
            UserRepository userRepository,
            SessionRepository sessionRepository,
            UserService userService,
            SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public UUID registerUser(String login, String password) {

        User user = null;

        try {
            user = userService.createUser(login, password);
        } catch (Exception e) {
            throw new UserAlreadyExists(login);
        }

        return sessionService.createSession(user).getId();
    }

    public UUID createSession(String login, String password) {

        var user = userService.getUser(login);

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new WrongCredentialsException();
        }

        return sessionService.createSession(user).getId();
    }

    public boolean authenticate(UUID id) {

        return sessionService.authenticate(id);
    }
}
