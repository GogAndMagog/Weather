package org.fizz_buzz.service;

import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.SessionRepository;
import org.fizz_buzz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Unit")
class RegistrationServiceTest {

    private static final String LOGIN = "user";
    private static final String PASSWORD = "pass";

    private static final long SESSION_DURATION_TIME = 60_000L; //1 minute in milliseconds

    private static final UUID SESSION_ID = UUID.fromString("7f834ff8-5493-4600-b911-150fc41b10b5");

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private RegistrationService registrationService;

    private User testUser;
    private Session testSession;

    @BeforeEach
    void setUp() {

        userRepository = mock(UserRepository.class);
        sessionRepository  = mock(SessionRepository.class);

        registrationService = new RegistrationService(userRepository, sessionRepository, SESSION_DURATION_TIME);

        testUser = createTestUser();
        testSession = createTestSession(testUser);
    }

    @Test
    void registerUser_CorrectData_NoException(){

        when(sessionRepository.save(any(Session.class))).thenReturn(testSession);

        assertEquals(SESSION_ID, registrationService.registerUser(LOGIN, PASSWORD));
    }

    @Test
    void registerUser_UserRepositoryException_Exception(){

        when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> registrationService.registerUser(LOGIN, PASSWORD));
    }

    @Test
    void registerUser_SessionRepositoryException_Exception(){

        when(sessionRepository.save(any(Session.class))).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> registrationService.registerUser(LOGIN, PASSWORD));
    }

    private User createTestUser(){

        User user = new User();
        user.setId(1L);
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);

        return user;
    }

    private Session createTestSession(User user){

        var session = new Session();
        session.setId(SESSION_ID);
        session.setUser(user);
        session.setExpiresAt(new Date(System.currentTimeMillis() + SESSION_DURATION_TIME));

        return session;
    }
}