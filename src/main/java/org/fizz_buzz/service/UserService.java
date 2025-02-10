package org.fizz_buzz.service;

import org.fizz_buzz.model.Location;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.LocationRepository;
import org.fizz_buzz.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public UserService(UserRepository userRepository,
                       LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    public User createUser(String username, String password) {
        return userRepository.save(new User(username, password));
    }

    public User getUser(String username) {
        return userRepository.findByLogin(username).orElse(null);
    }

    public List<Location> getUserLocations(String username) {
        return userRepository.findByLogin(username).map(locationRepository::findByUser).orElse(List.of());
    }

    public Location addLocation(User user, String locationName, Double latitude, Double longitude) {
        return locationRepository.save(new Location(locationName, user, latitude, longitude));
    }

    public void deleteLocation(User user, String locationName, Double latitude, Double longitude) {
        locationRepository.findByNameAndUserAndLongitudeAndLatitude(locationName, user, longitude, latitude)
                .ifPresent(locationRepository::delete);
    }
}
