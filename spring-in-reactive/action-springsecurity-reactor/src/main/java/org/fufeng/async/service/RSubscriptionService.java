
package org.fufeng.async.service;

import org.fufeng.mvc.model.StockSubscription;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface RSubscriptionService {
    Future<List<StockSubscription>> findByEmailAsync(String email);

    CompletableFuture<List<StockSubscription>> findByEmail(String email);

    Future<Void> addSubscriptionAsync(String email, String symbol);

    CompletableFuture<Void> addSubscription(String email, String symbol);
}
