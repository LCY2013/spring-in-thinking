package org.fufeng.servlet.jpa.dao;

import org.fufeng.servlet.jpa.entity.StockDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDao extends JpaRepository<StockDO, String> {
    StockDO findBySymbol(String symbol);
}
