package org.fufeng.project.config.consul.repository;

import org.fufeng.project.config.consul.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
