package org.fufeng.project.cloud.eureka.waiter.repository;

import org.fufeng.project.cloud.eureka.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
