package org.fizz_buzz.service;

import org.fizz_buzz.exception.IncorrectPassword;
import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.SessionRepository;
import org.fizz_buzz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@Tag("Unit")
class AuthenticationServiceTest {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";

    private static final long SESSION_DURATION_TIME = 60_000L; //1 minute in milliseconds

    private static final UUID SESSION_ID = UUID.fromString("7f834ff8-5493-4600-b911-150fc41b10b5");

    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    private User testUser;
    private Session testSession;

    private AuthenticationAndAuthorizationService authenticationAndAuthorizationService;

    @BeforeEach
    void setUp() {

        sessionRepository = mock(SessionRepository.class);
        userRepository = mock(UserRepository.class);

        authenticationAndAuthorizationService =
                new AuthenticationAndAuthorizationService(userRepository, sessionRepository, SESSION_DURATION_TIME);
    }

    @Test
    void isAuthorized_CorrectData_True() {

        testUser = createTestUser();
        testSession = createTestSession(testUser);

        when(sessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(testSession));

        assertTrue(authenticationAndAuthorizationService.isAuthorized(SESSION_ID));
    }

    @Test
    void isAuthorized_ExpiresAtExpired_False() {

        testUser = createTestUser();
        testSession = createTestSession(testUser);

        testSession.setExpiresAt(new Date(System.currentTimeMillis()));
        when(sessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(testSession));

        assertFalse(authenticationAndAuthorizationService.isAuthorized(SESSION_ID));
    }

    @Test
    void createSession_CorrectData_NoException() {

        testUser = createTestUser();
        testSession = createTestSession(testUser);

        when(userRepository.findByLogin(USERNAME)).thenReturn(Optional.of(testUser));

        when(sessionRepository.save(any(Session.class))).thenReturn(testSession);

        assertNotNull(authenticationAndAuthorizationService.authenticate(USERNAME, PASSWORD));
    }

    @Test
    void createSession_UserNotExist_Exception() {
        assertThrows(IncorrectPassword.class, () -> authenticationAndAuthorizationService.authenticate(USERNAME, PASSWORD));
    }

    private User createTestUser() {

        var user = new User();
        user.setId(1L);
        user.setLogin(USERNAME);
        user.setPassword(PASSWORD);

        return user;
    }

    private Session createTestSession(User user) {

        var session = new Session();
        session.setId(SESSION_ID);
        session.setUser(user);
        session.setExpiresAt(new Date(System.currentTimeMillis() + SESSION_DURATION_TIME));

        return session;
    }
}