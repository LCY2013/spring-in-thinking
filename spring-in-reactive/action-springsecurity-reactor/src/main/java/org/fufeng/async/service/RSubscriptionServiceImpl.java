package org.fufeng.async.service;

import org.fufeng.jpa.dao.StockDao;
import org.fufeng.jpa.dao.StockSubscriptionDao;
import org.fufeng.jpa.entity.StockDO;
import org.fufeng.jpa.entity.StockSubscriptionDO;
import org.fufeng.mvc.model.StockSubscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RSubscriptionServiceImpl implements RSubscriptionService {
    @Autowired
    private StockDao stockDao;

    @Autowired
    private StockSubscriptionDao subscriptionDao;

    @Async
    @Override
    public Future<List<StockSubscription>> findByEmailAsync(String email) {
        return new AsyncResult<>(doFindByEmail(email));
    }

    @Override
    public CompletableFuture<List<StockSubscription>> findByEmail(String email) {
        return CompletableFuture.supplyAsync(() -> doFindByEmail(email));
    }


    @Async
    @Override
    public Future<Void> addSubscriptionAsync(String email, String symbol) {
        doAddSubscription(email, symbol);
        return new AsyncResult<>(null);
    }

    @Override
    public CompletableFuture<Void> addSubscription(String email, String symbol) {
        return CompletableFuture.runAsync(() -> doAddSubscription(email, symbol));
    }

    public List<StockSubscription> doFindByEmail(String email) {
        log.info("do findByEmail for {}", email);
        List<StockSubscriptionDO> subscriptions = subscriptionDao.findByEmail(email);
        return subscriptions.stream().map(stockSubscriptionDO ->
                StockSubscription.builder()
                        .symbol(stockSubscriptionDO.getSymbol())
                        .email(stockSubscriptionDO.getEmail())
                        .build()
        ).collect(Collectors.toList());
    }

    public void doAddSubscription(String email, String symbol) {
        log.info("do addSubscription for {} for symbol {}", symbol);
        checkValidStock(symbol);
        checkSubscriptionExists(email, symbol);
        StockSubscriptionDO subscriptionDO = new StockSubscriptionDO();
        subscriptionDO.setEmail(email);
        subscriptionDO.setSymbol(symbol);
        subscriptionDao.save(subscriptionDO);
    }

    private void checkSubscriptionExists(String email, String symbol) {
        List<StockSubscriptionDO> matched = subscriptionDao.findByEmailAndSymbol(email, symbol);
        if (!CollectionUtils.isEmpty(matched)) {
            throw new RuntimeException("subscription already exists for this user");
        }
    }

    private void checkValidStock(String symbol) {
        Optional<StockDO> matched = stockDao.findAll()
                .stream()
                .filter(stockDO -> stockDO.getSymbol().equalsIgnoreCase(symbol))
                .findAny();
        if (!matched.isPresent()) {
            throw new RuntimeException("stock symbol not valid");
        }
    }

}
