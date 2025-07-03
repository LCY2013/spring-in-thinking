
package org.fufeng.mvc.service;

import org.fufeng.mvc.model.StockSubscription;

import java.util.List;

public interface SubscriptionService {
    List<StockSubscription> findByEmail(String email);

    void addSubscription(String email, String symbol);
}
