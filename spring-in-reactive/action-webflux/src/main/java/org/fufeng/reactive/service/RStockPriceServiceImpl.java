package org.fufeng.reactive.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import org.fufeng.r2dbc.dao.StockSubscriptionDao;
import org.fufeng.r2dbc.entity.StockSubscriptionDO;
import org.fufeng.reactive.dto.StockPrice;

@Service
@Slf4j
public class RStockPriceServiceImpl implements RStockPriceService {
    @Autowired
    private StockSubscriptionDao subscriptionDao;

    @Autowired
    private PriceQueryEngine priceQueryEngine;

    @Override
    public Flux<StockPrice> getPrice(String user) {
        return doGetPrice(user);
    }

    public Flux<StockPrice> doGetPrice(String email) {
        log.info("do getPrice for {}", email);
        Flux<StockSubscriptionDO> subscriptions = subscriptionDao.findByEmail(email);
        return subscriptions
                .map(stockSubscriptionDO -> stockSubscriptionDO.getSymbol())
                .map(symbol -> new StockPrice(symbol, priceQueryEngine.getPriceForSymbol(symbol)));
    }
}
