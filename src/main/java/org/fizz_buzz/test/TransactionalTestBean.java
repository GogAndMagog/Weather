package org.fizz_buzz.test;

import org.fizz_buzz.model.Location;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.LocationRepository;
import org.fizz_buzz.repository.UserRepository;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

import jakarta.transaction.Transactional;

@Component
public class TransactionalTestBean {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";

    private UserRepository userRepository;
    private LocationRepository locationRepository;

    public TransactionalTestBean(UserRepository userRepository, LocationRepository locationRepository) {

        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional
    public void test(){

        var testUser = createTestUser();
        testUser.addLocation(new Location("Texas", 12.0, 13.0));
        testUser.addLocation(new Location("Bamboo", 45.0, 67.0));
        userRepository.save(testUser);

        userRepository.findAll().forEach(System.out::println);
    }

    private User createTestUser() {

        var user = new User();
        user.setLogin(USERNAME);
        user.setPassword(PASSWORD);

        return user;
    }
}
