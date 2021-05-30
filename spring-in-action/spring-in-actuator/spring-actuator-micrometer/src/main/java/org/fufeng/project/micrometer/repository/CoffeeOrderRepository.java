package org.fufeng.project.micrometer.repository;

import org.fufeng.project.micrometer.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
