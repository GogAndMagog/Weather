package org.fizz_buzz.service;

import org.fizz_buzz.exception.WrongCredentialsException;
import org.fizz_buzz.exception.UserNotExist;
import org.fizz_buzz.model.Location;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.LocationRepository;
import org.fizz_buzz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    public User createUser(String username, String password) {
        return userRepository.save(new User(username, password));
    }

    public User getUser(String username) {
        return userRepository.findByLogin(username)
                .orElseThrow(UserNotExist::new);
    }

    public List<Location> getUserLocations(String username) {
        return locationRepository.findByUserLogin(username);
    }

    @Transactional
    public void addLocation(User user, String locationName, Double latitude, Double longitude) {

        user.addLocation(new Location(locationName, latitude, longitude));
        userRepository.save(user);
    }

    @Transactional
    public void deleteLocation(User user, String locationName) {

        user.removeLocation(locationName);
        userRepository.save(user);
    }

    @Transactional
    public void deleteLocation(User user, Location location) {

        user.removeLocation(location);
        userRepository.save(user);
    }

    @Transactional
    public void deleteLocation(User user, Long locationId) {

        var location = user.removeLocation(locationId);
//        locationRepository.save(location);
        userRepository.save(user);
    }

}
