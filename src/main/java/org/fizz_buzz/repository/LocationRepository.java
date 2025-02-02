package org.fizz_buzz.repository;

import org.fizz_buzz.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
