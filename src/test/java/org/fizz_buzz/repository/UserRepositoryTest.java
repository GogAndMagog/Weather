package org.fizz_buzz.repository;

import org.fizz_buzz.common.CustomTestTag;
import org.fizz_buzz.config.DataLayerTestConfig;
import org.fizz_buzz.model.Location;
import org.fizz_buzz.model.User;
import org.fizz_buzz.test.TransactionalTestBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@Tag(CustomTestTag.INTEGRATION)
class UserRepositoryTest {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";
    private static final String NEW_PASSWORD = "pazz";

    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private TransactionalTestBean transactionalTestBean;


    @BeforeEach
    void setUp() {
        var tag = CustomTestTag.INTEGRATION.toString();
        ApplicationContext context = new AnnotationConfigApplicationContext(DataLayerTestConfig.class);

        userRepository = context.getBean(UserRepository.class);
        locationRepository = context.getBean(LocationRepository.class);
        transactionalTestBean = context.getBean(TransactionalTestBean.class);
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
        testUser.setPassword(NEW_PASSWORD);

        assertDoesNotThrow(() -> userRepository.save(testUser));
    }

    @Test
    void findById_Successful() {

        var testUser = createTestUser();
        userRepository.save(testUser);
        var id = testUser.getId();

        assertTrue(userRepository.findById(id).isPresent());
    }

    @Test
    void findById_WrongId_NullValue() {

        var testUser = createTestUser();
        userRepository.save(testUser);

        assertNull(userRepository.findById(30L).orElse(null));
    }

    @Test
    @Disabled("Repository changed")
    void getLocations_CorrectData_NoException() {

//        var testUser = createTestUser();
//        testUser.addLocation(new Location("Texas", 12.0, 13.0));
//        testUser.addLocation(new Location("Bamboo", 45.0, 67.0));
//        userRepository.save(testUser);
////        transactionalTestBean.test();
//
//
//        userRepository.findByLogin(USERNAME)
//                .ifPresent(user -> assertEquals(2, user.getLocations().size()));
    }

    @Test
    void removeLocation_LocationName_True(){

        var testUser = createTestUser();
        testUser.addLocation(createTexasLocation(testUser));
        testUser.addLocation(createBambooLocation(testUser));

        userRepository.save(testUser);

        assertTrue(testUser.removeLocation("Bamboo"));
    }

    @Test
    void removeLocation_LocationName_LocationDoesNotExist_False(){

        var testUser = createTestUser();
        userRepository.save(testUser);

        assertFalse(testUser.removeLocation("Bamboo"));
    }

    @Test
    void removeLocation_Location_True(){

        var testUser = createTestUser();
        testUser.addLocation(createTexasLocation(testUser));
        testUser.addLocation(createBambooLocation(testUser));

        userRepository.save(testUser);

//        assertTrue(testUser.removeLocation(testUser.getLocations().get(1)));
    }

    private Location createTexasLocation(User user) {
        return new Location("Texas", 12.0, 13.0);
    }

    private Location createBambooLocation(User user) {
        return new Location("Bamboo", 12.0, 13.0);
    }


    private User createTestUser() {

        var user = new User();
        user.setLogin(USERNAME);
        user.setPassword(PASSWORD);

        return user;
    }
}