package org.fufeng.r2dbc.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import org.fufeng.r2dbc.entity.StockSubscriptionDO;

public interface StockSubscriptionDao extends ReactiveCrudRepository<StockSubscriptionDO, Long> {
    Flux<StockSubscriptionDO> findByEmail(String email);

    Flux<StockSubscriptionDO> findByEmailAndSymbol(String email, String symbol);
}
