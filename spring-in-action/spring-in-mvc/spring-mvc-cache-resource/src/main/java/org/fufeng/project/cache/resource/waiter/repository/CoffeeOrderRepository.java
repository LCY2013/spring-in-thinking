package org.fufeng.project.cache.resource.waiter.repository;

import org.fufeng.project.cache.resource.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
