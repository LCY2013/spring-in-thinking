package org.fufeng.project.nacos.waiter.repository;

import org.fufeng.project.nacos.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
