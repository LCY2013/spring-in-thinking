package org.fufeng.reactive.rsocket.server;

import org.fufeng.reactive.dto.StockPrice;
import org.fufeng.reactive.model.Stock;
import org.fufeng.reactive.service.PriceQueryEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class RSocketController {
    @Autowired
    public PriceQueryEngine engine;

    @MessageMapping("currentStockPrice")
    public Mono<StockPrice> getStockPrice(Stock stock) {
        System.out.println("Receive symbol from client");
        return Mono.just(new StockPrice(stock.getSymbol(), engine.getPriceForSymbol(stock.getSymbol())));
    }
}
