package orf.fufeng.action.jpa.dao;

import orf.fufeng.action.jpa.entity.StockDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockDao extends JpaRepository<StockDO, String> {
    StockDO findBySymbol(String symbol);
}
