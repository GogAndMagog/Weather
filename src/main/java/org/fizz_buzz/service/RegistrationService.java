package org.fizz_buzz.service;

import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.SessionRepository;
import org.fizz_buzz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@PropertySource("classpath:application.properties")
public class RegistrationService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    @Value("${sessionDurationTime}")
    private long sessionDurationTime;

    public RegistrationService(UserRepository userRepository,
                               SessionRepository sessionRepository){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public UUID registerUser(String login, String password){
        var user = new User(login, password);
        userRepository.save(user);

        var session = new Session();
        session.setUser(user);
        session.setExpiresAt(new Date(System.currentTimeMillis() + sessionDurationTime));

        return sessionRepository.save(session).getId();
    }
}
