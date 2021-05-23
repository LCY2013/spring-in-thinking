package org.fufeng.project.aop.repository;

import org.fufeng.project.aop.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
