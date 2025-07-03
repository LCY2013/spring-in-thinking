package org.fufeng.async.service;

import org.fufeng.jpa.dao.StockSubscriptionDao;
import org.fufeng.jpa.entity.StockSubscriptionDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RStockPriceServiceImpl implements RStockPriceService {
    @Autowired
    private StockSubscriptionDao subscriptionDao;

    @Autowired
    private PriceQueryEngine priceQueryEngine;

    @Async
    @Override
    public Future<Map<String, String>> getPriceAsync(String user) {
        return new AsyncResult<>(doGetPrice(user));
    }

    @Override
    public CompletableFuture<Map<String, String>> getPrice(String user) {
        return CompletableFuture.supplyAsync(() -> doGetPrice(user));
    }

    public Map<String, String> doGetPrice(String email) {
        log.info("do getPrice for {}", email);
        List<StockSubscriptionDO> subscriptions = subscriptionDao.findByEmail(email);
        Map<String, String> priceMap = subscriptions.stream()
                .map(subscription -> subscription.getSymbol())
                .collect(Collectors.toMap(
                                Function.identity(),
                                priceQueryEngine::getPriceForSymbol
                        )
                );
        return priceMap;
    }
}
