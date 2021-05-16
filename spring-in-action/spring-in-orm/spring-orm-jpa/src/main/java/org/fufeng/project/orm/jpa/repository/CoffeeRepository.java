package org.fufeng.project.orm.jpa.repository;

import org.fufeng.project.orm.jpa.model.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
