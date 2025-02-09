package org.fizz_buzz.service;

import org.fizz_buzz.exception.IncorrectPassword;
import org.fizz_buzz.model.Session;
import org.fizz_buzz.repository.SessionRepository;
import org.fizz_buzz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@PropertySource("classpath:application.properties")
public class AuthenticationAndAuthorizationService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Value("${sessionDurationTime}")
    private long sessionDurationTime;

    public AuthenticationAndAuthorizationService(
            UserRepository userRepository,
            SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public UUID authenticate(String login, String password) {
        return userRepository
                .findByLogin(login)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> {
                    var session = new Session();
                    session.setUser(user);
                    session.setExpiresAt(new Date(System.currentTimeMillis() + sessionDurationTime));

                    return sessionRepository
                            .save(session)
                            .getId();
                })
                .orElseThrow(IncorrectPassword::new);
    }

    public boolean isAuthorized(UUID sessionId) {
        return sessionRepository
                .findById(sessionId)
                .filter(value -> (value.getExpiresAt().getTime() - System.currentTimeMillis()) > 0)
                .isPresent();
    }
}
