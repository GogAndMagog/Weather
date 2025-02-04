package org.fizz_buzz.repository;

import org.fizz_buzz.config.JPATestConfig;
import org.fizz_buzz.model.Location;
import org.fizz_buzz.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class LocationRepositoryTest {

    private static final String userName = "user";
    private static final String userPass = "pass";

    private LocationRepository locationRepository;
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(JPATestConfig.class);

        locationRepository = context.getBean(LocationRepository.class);
        userRepository = context.getBean(UserRepository.class);

        var testUser = new User();
        testUser.setLogin(userName);
        testUser.setPassword(userPass);

        userRepository.save(testUser);
    }

    @Test
    void save_CorrectData_NoExceptions() {
        var location = new Location();
        location.setName("Ust` Perd`uisk");
        userRepository
                .findByLogin(userName)
                .ifPresent(location::setUserId);
        location.setLatitude(12);
        location.setLongitude(21);

        var savedLocation = locationRepository.save(location);
        assertNotNull(savedLocation);
    }

    @Test
    void save_IncorrectUser_Exceptions() {
        var location = new Location();
        var user = new User();

        user.setId(2);
        user.setLogin(userName + '2');
        user.setPassword(userPass);

        location.setName("Ust` Perd`uisk");
        location.setUserId(user);
        location.setLatitude(12);
        location.setLongitude(21);

        assertThrows(Exception.class, () -> locationRepository.save(location));
    }
}