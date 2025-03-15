package org.fizz_buzz.service;

import jakarta.servlet.http.HttpServletRequest;
import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
@PropertySource("classpath:application.properties")
public class SessionService {

    @Value("${sessionDurationTime}")
    private long sessionDurationTime;

    @Value("${sessionId}")
    private String sessionId;

    private SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session getSession(UUID id) {
        return sessionRepository.findById(id).orElseThrow();
    }

    public Session createSession(User user) {
        var session = new Session();
        session.setUser(user);
        session.setExpiresAt(new Date(System.currentTimeMillis() + sessionDurationTime));
        return sessionRepository.save(session);
//        return sessionRepository.save(new Session(user, new Date(System.currentTimeMillis() + sessionDurationTime)));
    }

    public boolean authenticate(UUID id) {
        return sessionRepository.existsByIdAndExpiresAtAfter(id, new Date());
    }

    public Session getSession(HttpServletRequest request) throws NoSuchFieldException {

        var sessionCookie = Arrays.stream(request.getCookies())
                .filter(cookie ->
                        cookie.getName().equals(sessionId)
                                && !cookie.getValue().isEmpty())
                .findAny()
                .orElseThrow(NoSuchFieldException::new);
        return sessionRepository.findById(UUID.fromString(sessionCookie.getValue())).orElseThrow();
    }
}
