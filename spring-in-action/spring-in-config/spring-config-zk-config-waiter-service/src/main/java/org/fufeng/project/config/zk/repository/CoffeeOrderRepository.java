package org.fufeng.project.config.zk.repository;

import org.fufeng.project.config.zk.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
