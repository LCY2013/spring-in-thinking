package org.fufeng.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.fufeng.jpa.entity.StockDO;

public interface StockDao extends JpaRepository<StockDO, String> {
    StockDO findBySymbol(String symbol);
}
