package org.fufeng.reactive.service;

import org.fufeng.reactive.model.Stock;
import reactor.core.publisher.Flux;

public interface RStockService {
    Flux<Stock> getAllStocks();
}
