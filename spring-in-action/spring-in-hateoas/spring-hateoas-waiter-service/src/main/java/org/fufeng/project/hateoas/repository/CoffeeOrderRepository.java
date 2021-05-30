package org.fufeng.project.hateoas.repository;

import org.fufeng.project.hateoas.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
