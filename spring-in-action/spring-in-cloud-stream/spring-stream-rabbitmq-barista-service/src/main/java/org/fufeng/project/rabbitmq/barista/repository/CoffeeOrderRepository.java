package org.fufeng.project.rabbitmq.barista.repository;

import org.fufeng.project.rabbitmq.barista.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
