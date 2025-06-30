
package orf.fufeng.action.mvc.service;

import orf.fufeng.action.mvc.model.StockSubscription;

import java.util.List;

public interface SubscriptionService {
    List<StockSubscription> findByEmail(String email);

    void addSubscription(String email, String symbol);
}
