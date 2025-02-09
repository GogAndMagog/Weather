package org.fizz_buzz.repository;

import org.fizz_buzz.config.DataLayerTestConfig;
import org.fizz_buzz.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Integration")
class UserRepositoryTest {

    private static final String username = "user";
    private static final String password = "pass";
    private static final String newPassword = "pazz";

    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataLayerTestConfig.class);

        userRepository = context.getBean(UserRepository.class);
    }

    @Test
    void save_CorrectData_NoException() {
        var testUser = createTestUser();

        userRepository.save(testUser);

        assertNotEquals(0, testUser.getId());
    }

    @Test
    void save_NullPasswordAndNullUsername_Exception() {
        var testUser = new User();

        assertThrows(Exception.class, () -> userRepository.save(testUser));
    }

    @Test
    void update_CorrectData_NoException() {
        var testUser = createTestUser();

        userRepository.save(testUser);
        testUser.setPassword(newPassword);

        assertDoesNotThrow(() -> userRepository.save(testUser));
    }

    @Test
    void findById_CorrectData_NoException() {
        var testUser = createTestUser();
        userRepository.save(testUser);

        assertEquals(testUser, userRepository.findById(testUser.getId()).orElse(null));
    }

    @Test
    void findById_WrongId_NullValue() {
        var testUser = createTestUser();
        userRepository.save(testUser);

        assertNull(userRepository.findById(30L).orElse(null));
    }

    private User createTestUser() {
        var user = new User();
        user.setLogin(username);
        user.setPassword(password);

        return user;
    }
}