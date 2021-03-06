package org.fufeng.project.rabbitmq.waiter.repository;

import org.fufeng.project.rabbitmq.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
