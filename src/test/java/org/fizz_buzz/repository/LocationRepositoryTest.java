package org.fizz_buzz.repository;

import org.fizz_buzz.common.CustomTestTag;
import org.fizz_buzz.config.DataLayerTestConfig;
import org.fizz_buzz.model.Location;
import org.fizz_buzz.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Tag(CustomTestTag.INTEGRATION)
@Disabled("Model has changed")
class LocationRepositoryTest {

    private static final String LOGIN = "user";
    private static final String PASS = "pass";

    private static final String LOCATION_NAME_TEXAS = "Texas";
    private static final String LOCATION_NAME_CLOUDS = "Clouds";

    private LocationRepository locationRepository;
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {

        ApplicationContext context = new AnnotationConfigApplicationContext(DataLayerTestConfig.class);

        locationRepository = context.getBean(LocationRepository.class);
        userRepository = context.getBean(UserRepository.class);
    }

    @Test
    void save_CorrectData_NoExceptions() {

        var user = userRepository.save(createTestUser());

        var location = createTestLocation(user, LOCATION_NAME_TEXAS, 112.12, 11.23);

        assertNotEquals(0, locationRepository.save(location).getId());
    }

    @Test
    void save_IncorrectUser_Exceptions() {

        var user = createTestUser();
        user.setId(2L);

        var location = createTestLocation(user, LOCATION_NAME_TEXAS, 12, 14);

        assertThrows(Exception.class, () -> locationRepository.save(location));
    }

    @Test
    void findByUser_CorrectData_NoException() {
        var user = userRepository.save(createTestUser());

        var location = createTestLocation(user, LOCATION_NAME_TEXAS, 112.12, 11.23);
        locationRepository.save(location);

        location = createTestLocation(user, LOCATION_NAME_CLOUDS, 331, 12231.23);
        locationRepository.save(location);


        assertEquals(2, locationRepository.findByUser(user).size());
    }

    private User createTestUser(){

        var testUser = new User();
        testUser.setLogin(LOGIN);
        testUser.setPassword(PASS);

        return testUser;
    }

    private Location createTestLocation(User user,
                                        String name,
                                        double latitude,
                                        double longitude ){

        var testLocation = new Location();
//        testLocation.setUser(user);
        testLocation.setName(name);
        testLocation.setLatitude(latitude);
        testLocation.setLongitude(longitude);

        return testLocation;
    }
}