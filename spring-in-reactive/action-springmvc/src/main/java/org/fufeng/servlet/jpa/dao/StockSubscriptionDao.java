package org.fufeng.servlet.jpa.dao;

import org.fufeng.servlet.jpa.entity.StockSubscriptionDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockSubscriptionDao extends JpaRepository<StockSubscriptionDO, Long> {
    List<StockSubscriptionDO> findByEmail(String email);

    List<StockSubscriptionDO> findByEmailAndSymbol(String email, String symbol);
}
