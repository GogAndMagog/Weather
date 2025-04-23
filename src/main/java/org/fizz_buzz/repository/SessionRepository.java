package org.fizz_buzz.repository;

import org.fizz_buzz.model.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends CrudRepository<Session, UUID> {

    boolean existsByIdAndExpiresAtAfter(UUID id, Date expiresAt);

    @Override
    Optional<Session> findById(UUID uuid);

    void deleteByExpiresAtBefore(Date date);
}
