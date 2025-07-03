package org.fufeng.jpa.dao;

import org.fufeng.jpa.entity.StockDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDao extends JpaRepository<StockDO, String> {
    StockDO findBySymbol(String symbol);
}
