package org.fufeng.project.orm.jpa.repository;

import org.fufeng.project.orm.jpa.model.CoffeeOrder;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeOrderRepository extends CrudRepository<CoffeeOrder, Long> {
}
