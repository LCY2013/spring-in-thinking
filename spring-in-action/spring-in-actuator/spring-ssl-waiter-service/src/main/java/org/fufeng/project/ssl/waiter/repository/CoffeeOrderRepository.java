package org.fufeng.project.ssl.waiter.repository;

import org.fufeng.project.ssl.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
