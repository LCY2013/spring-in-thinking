
package org.fufeng.reactive.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.fufeng.reactive.model.StockSubscription;

public interface RSubscriptionService {
    Flux<StockSubscription> findByEmail(String email);

    Mono<Void> addSubscription(String email, String symbol);
}
