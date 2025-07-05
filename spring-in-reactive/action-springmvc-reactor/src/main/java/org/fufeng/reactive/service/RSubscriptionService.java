
package org.fufeng.reactive.service;

import org.fufeng.reactive.model.StockSubscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RSubscriptionService {
    Flux<StockSubscription> findByEmail(String email);

    Mono<Void> addSubscription(String email, String symbol);
}
