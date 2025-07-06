package org.fufeng.reactive.service;

import org.fufeng.r2dbc.dao.StockDao;
import org.fufeng.r2dbc.dao.StockSubscriptionDao;
import org.fufeng.r2dbc.entity.StockSubscriptionDO;
import org.fufeng.reactive.model.StockSubscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RSubscriptionServiceImpl implements RSubscriptionService {
    @Autowired
    private StockDao stockDao;

    @Autowired
    private StockSubscriptionDao subscriptionDao;

    @Override
    public Flux<StockSubscription> findByEmail(String email) {
        return doFindByEmail(email);
    }

    @Override
    public Mono<Void> addSubscription(String email, String symbol) {
        return doAddSubscription(email, symbol).then();
    }

    public Flux<StockSubscription> doFindByEmail(String email) {
        log.info("do findByEmail for {}", email);
        return subscriptionDao.findByEmail(email)
                .map(stockSubscriptionDO ->
                        StockSubscription.builder()
                                .symbol(stockSubscriptionDO.getSymbol())
                                .email(stockSubscriptionDO.getEmail())
                                .build()
                );
    }

    public Mono<StockSubscriptionDO> doAddSubscription(String email, String symbol) {
        log.info("do addSubscription for {} for symbol {}", symbol);
        return stockDao.findAll()
                .filter(stockDO -> stockDO.getSymbol().equalsIgnoreCase(symbol))
                .switchIfEmpty(Mono.error(new RuntimeException("stock symbol not valid")))
                .then(
                        checkSubscriptionExists(email, symbol)
                                .hasElements()
                                .flatMap(hasElement -> {
                                    if (!hasElement) {
                                        StockSubscriptionDO subscriptionDO = new StockSubscriptionDO();
                                        subscriptionDO.setEmail(email);
                                        subscriptionDO.setSymbol(symbol);
                                        return subscriptionDao.save(subscriptionDO);
                                    }
                                    return null;
                                })
                                .switchIfEmpty(Mono.error(new RuntimeException("subscription already exists for this user")))
                );
    }

    private Flux<StockSubscriptionDO> checkSubscriptionExists(String email, String symbol) {
        return subscriptionDao.findByEmailAndSymbol(email, symbol);
//            throw new RuntimeException("subscription already exists for this user");
    }

//    private void checkValidStock(String symbol) {
//        stockDao.findAll()
//                .filter(stockDO -> stockDO.getSymbol().equalsIgnoreCase(symbol))
//                .findAny();
//        if (!matched.isPresent()) {
//            throw new RuntimeException("stock symbol not valid");
//        }
//    }

}
