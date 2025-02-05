package org.fizz_buzz.repository;

import org.fizz_buzz.model.Location;
import org.fizz_buzz.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> findByUser(User user);
}
