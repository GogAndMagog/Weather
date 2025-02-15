package org.fizz_buzz.service;

import org.fizz_buzz.common.CustomTestTag;
import org.fizz_buzz.config.DataLayerTestConfig;
import org.fizz_buzz.model.Location;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.LocationRepository;
import org.fizz_buzz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@Tag(CustomTestTag.INTEGRATION)
class UserServiceTest {

    private final String TEST_LOGIN = "test";
    private final String TEST_PASSWORD = "123";

    private final String LOCATION_NY = "NY";
    private final String LOCATION_MSK = "MSK";
    private final String LOCATION_RIO = "RIO";

    private UserService userService;


    @BeforeEach
    void setUp() {

        ApplicationContext context = new AnnotationConfigApplicationContext(DataLayerTestConfig.class);

        UserRepository userRepository = context.getBean(UserRepository.class);
        LocationRepository locationRepository = context.getBean(LocationRepository.class);

        userService = new UserService(userRepository, locationRepository);
    }

    @Test
    void createUser_NoException() {
        assertNotNull(userService.createUser(TEST_LOGIN, TEST_PASSWORD));
    }

    @Test
    void getUser_NoException() {

        createUser(TEST_LOGIN, TEST_PASSWORD);

        assertNotNull(userService.getUser(TEST_LOGIN, TEST_PASSWORD));
    }

    @Test
    void addLocation_NoException() {

        var user = createUser(TEST_LOGIN, TEST_PASSWORD);

//        assertNotNull(userService.addLocation(user, LOCATION_NY, 133.7, 666.0));
    }

    @Test
    void getLocations_NoException() {

        var user = createUser(TEST_LOGIN, TEST_PASSWORD);
//        userService.addLocation(user, LOCATION_NY, 133.7, 666.0);
//        userService.addLocation(user, LOCATION_MSK, 14.77, 704.0);
//        userService.addLocation(user, LOCATION_RIO, 880.0, 555.3535);

        assertEquals(3, userService.getUserLocations(TEST_LOGIN).size());
    }

    @Test
    void deleteLocation_NoException() {

        var user = createUser(TEST_LOGIN, TEST_PASSWORD);
//        userService.addLocation(user, LOCATION_NY, 133.7, 666.0);
//        userService.addLocation(user, LOCATION_MSK, 14.77, 704.0);
//        userService.addLocation(user, LOCATION_RIO, 880.0, 555.3535);

//        userService.deleteLocation(user, LOCATION_NY, 133.7, 666.0);

//        assertEquals(2, userService.getUserLocations(TEST_LOGIN).size());
    }

    private User createUser(String login, String password) {
        return userService.createUser(login, password);
    }

}