package org.fizz_buzz.repository;

import org.fizz_buzz.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    @Query(value = "SELECT User FROM User JOIN FETCH User.locations WHERE User.login =: login")
    @EntityGraph(value = "user-entity-graph")
    Optional<User> findByLogin(String login);
}
