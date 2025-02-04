package org.fizz_buzz.service;

import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.SessionRepository;
import org.fizz_buzz.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class RegistrationService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private long sessionDurationTime;

    public RegistrationService(UserRepository userRepository,
                               SessionRepository sessionRepository,
                               long sessionDurationTime){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.sessionDurationTime = sessionDurationTime;
    }

    public UUID registerUser(String login, String password){
        var user = new User();
        user.setLogin(login);
        user.setPassword(password);
        userRepository.save(user);

        var session = new Session();
        session.setUser(user);
        session.setExpiresAt(new Date(System.currentTimeMillis() + sessionDurationTime));

        return sessionRepository.save(session).getId();
    }
}
