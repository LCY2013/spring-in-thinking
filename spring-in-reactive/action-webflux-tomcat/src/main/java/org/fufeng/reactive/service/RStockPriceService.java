package org.fufeng.reactive.service;

import org.fufeng.reactive.dto.StockPrice;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface RStockPriceService {
    Flux<StockPrice> getPrice(String user);
}
