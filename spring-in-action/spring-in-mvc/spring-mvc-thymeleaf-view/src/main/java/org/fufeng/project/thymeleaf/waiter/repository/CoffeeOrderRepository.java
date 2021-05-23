package org.fufeng.project.thymeleaf.waiter.repository;

import org.fufeng.project.thymeleaf.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
