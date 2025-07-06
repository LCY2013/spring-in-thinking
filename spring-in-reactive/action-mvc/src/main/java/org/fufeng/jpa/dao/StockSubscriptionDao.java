package org.fufeng.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.fufeng.jpa.entity.StockSubscriptionDO;

import java.util.List;

public interface StockSubscriptionDao extends JpaRepository<StockSubscriptionDO, Long> {
    List<StockSubscriptionDO> findByEmail(String email);

    List<StockSubscriptionDO> findByEmailAndSymbol(String email, String symbol);
}
