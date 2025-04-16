package org.fizz_buzz.service;

import org.fizz_buzz.common.CustomTestTag;
import org.fizz_buzz.exception.IncorrectPassword;
import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.LocationRepository;
import org.fizz_buzz.repository.SessionRepository;
import org.fizz_buzz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@Tag(CustomTestTag.UNIT)
class AuthenticationServiceTest {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";

    private static final long SESSION_DURATION_TIME = 60_000L; //1 minute in milliseconds

    private static final UUID SESSION_ID = UUID.fromString("7f834ff8-5493-4600-b911-150fc41b10b5");

    private SessionRepository sessionRepository;
    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private UserService userService;
    private SessionService sessionService;


    private User testUser;
    private Session testSession;

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {

        sessionRepository = mock(SessionRepository.class);
        userRepository = mock(UserRepository.class);
        locationRepository = mock(LocationRepository.class);
//        userService = mock(UserService.class);
//        sessionService = mock(SessionService.class);
        userService = new UserService(userRepository, locationRepository);
        sessionService = new SessionService(sessionRepository);

        authenticationService =
                new AuthenticationService(userRepository,
                        sessionRepository,
                        userService,
                        sessionService);
    }

    @Test
    void isAuthorized_CorrectData_True() {

        testUser = createTestUser();
        testSession = createTestSession(testUser);

        when(sessionRepository.existsByIdAndExpiresAtAfter(any(UUID.class), any(Date.class))).thenReturn(true);

        assertTrue(authenticationService.authenticate(SESSION_ID));
    }

    @Test
    void isAuthorized_ExpiresAtExpired_False() {

        testUser = createTestUser();
        testSession = createTestSession(testUser);

        testSession.setExpiresAt(new Date(System.currentTimeMillis()));
        when(sessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(testSession));

        assertFalse(authenticationService.authenticate(SESSION_ID));
    }

    @Test
    void createSession_UserNotExist_Exception() {
        assertThrows(RuntimeException.class, () -> authenticationService.createSession(USERNAME, PASSWORD));
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