package org.fizz_buzz.repository;

import org.fizz_buzz.model.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
}
