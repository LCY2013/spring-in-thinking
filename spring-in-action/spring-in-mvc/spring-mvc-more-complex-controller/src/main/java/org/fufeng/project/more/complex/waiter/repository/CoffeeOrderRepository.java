package org.fufeng.project.more.complex.waiter.repository;

import org.fufeng.project.more.complex.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
