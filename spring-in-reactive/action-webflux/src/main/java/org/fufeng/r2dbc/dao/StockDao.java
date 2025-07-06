package org.fufeng.r2dbc.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import org.fufeng.r2dbc.entity.StockDO;

public interface StockDao extends ReactiveCrudRepository<StockDO, String> {
    Mono<StockDO> findBySymbol(String symbol);
}
