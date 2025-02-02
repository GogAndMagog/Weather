package org.fizz_buzz.repository;

import org.fizz_buzz.config.JPATestConfig;
import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SessionRepositoryTest {

    private static final String username = "user";
    private static final String password = "pass";

    private SessionRepository sessionRepository;
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(JPATestConfig.class);

        sessionRepository = context.getBean(SessionRepository.class);
        userRepository = context.getBean(UserRepository.class);
    }

    @Test
    void save_CorrectData_NoException() {
        var user = createTestUser();
        userRepository.save(user);
        var session = new Session();

        session.setUser(user);
        session.setExpiresAt(new Date(System.currentTimeMillis()));

        assertDoesNotThrow(() -> sessionRepository.save(session));
    }

    @Test
    void save_nonexistentUser_Exception() {
        var user = createTestUser();

        var session = new Session();
        session.setUser(user);
        session.setExpiresAt(new Date(System.currentTimeMillis()));

        assertThrows(Exception.class, () -> sessionRepository.save(session));
    }

    @Test
    void save_nonexistentExpiresAtDate_Exception() {
        var user = createTestUser();
        userRepository.save(user);

        var session = new Session();
        session.setUser(user);

        assertThrows(Exception.class, () -> sessionRepository.save(session));
    }

    private User createTestUser() {
        var user = new User();
        user.setLogin(username);
        user.setPassword(password);

        return user;
    }
}