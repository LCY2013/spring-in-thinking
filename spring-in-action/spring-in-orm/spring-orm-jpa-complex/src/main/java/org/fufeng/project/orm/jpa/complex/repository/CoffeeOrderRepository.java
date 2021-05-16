package org.fufeng.project.orm.jpa.complex.repository;

import org.fufeng.project.orm.jpa.complex.model.CoffeeOrder;

import java.util.List;

public interface CoffeeOrderRepository extends BaseRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByCustomerOrderById(String customer);
    List<CoffeeOrder> findByItems_Name(String name);
}
