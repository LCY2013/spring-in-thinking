package org.fufeng.project.jar.exec.repository;

import org.fufeng.project.jar.exec.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
