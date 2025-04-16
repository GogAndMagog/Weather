package org.fizz_buzz.repository;

import org.fizz_buzz.model.Location;
import org.fizz_buzz.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long>, PagingAndSortingRepository<Location, Long> {
    @Override
    @EntityGraph(attributePaths = {"user"})
    Iterable<Location> findAll();

    @Override
    @EntityGraph(attributePaths = {"user"})
    Page<Location> findAll(Pageable pageable);

    List<Location> findByUser(User user);
    Optional<Location> findByNameAndUserAndLongitudeAndLatitude(String name,
                                                                User user,
                                                                Double longitude,
                                                                Double latitude);

    @EntityGraph(attributePaths = {"user"})
    List<Location> findByUserLogin(String login);

//    void deleteByLocation(Location location);
}
