package org.fizz_buzz.repository;

import org.fizz_buzz.model.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @EntityGraph(attributePaths = {"locations"})
    Optional<User> findByLogin(String login);

    @EntityGraph(attributePaths = {"locations"})
    Optional<User> findByLoginAndPassword(String login, String password);

//    @Override
//    @Query("select u from User u join fetch Location l on u.id = l.user.id")
//    Iterable<User> findAll();
}
