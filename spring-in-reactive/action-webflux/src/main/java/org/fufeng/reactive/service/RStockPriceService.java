package org.fufeng.reactive.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import org.fufeng.reactive.dto.StockPrice;

@Service
public interface RStockPriceService {
    Flux<StockPrice> getPrice(String user);
}
