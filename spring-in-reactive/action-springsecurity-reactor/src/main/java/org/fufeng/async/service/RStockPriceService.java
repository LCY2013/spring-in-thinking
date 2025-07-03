package org.fufeng.async.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public interface RStockPriceService {
    Future<Map<String, String>> getPriceAsync(String user);

    CompletableFuture<Map<String, String>> getPrice(String user);
}
