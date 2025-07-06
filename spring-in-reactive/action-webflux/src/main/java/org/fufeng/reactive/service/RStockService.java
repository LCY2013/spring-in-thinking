package org.fufeng.reactive.service;

import reactor.core.publisher.Flux;
import org.fufeng.reactive.model.Stock;

public interface RStockService {
    Flux<Stock> getAllStocks();
}
