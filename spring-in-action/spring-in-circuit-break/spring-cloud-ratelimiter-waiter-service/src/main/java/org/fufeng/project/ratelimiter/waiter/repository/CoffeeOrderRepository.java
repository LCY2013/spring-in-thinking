package org.fufeng.project.ratelimiter.waiter.repository;

import org.fufeng.project.ratelimiter.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
