
package org.fufeng.servlet.mvc.service;


import org.fufeng.servlet.mvc.model.StockSubscription;

import java.util.List;

public interface SubscriptionService {
    List<StockSubscription> findByEmail(String email);

    void addSubscription(String email, String symbol);
}
