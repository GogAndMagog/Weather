package org.fizz_buzz.repository;

import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends CrudRepository<Session, UUID> {
    Optional<Session> findByUser(User user);
}
