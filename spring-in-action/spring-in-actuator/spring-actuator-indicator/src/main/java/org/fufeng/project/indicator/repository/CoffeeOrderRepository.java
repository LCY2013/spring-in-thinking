package org.fufeng.project.indicator.repository;

import org.fufeng.project.indicator.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
