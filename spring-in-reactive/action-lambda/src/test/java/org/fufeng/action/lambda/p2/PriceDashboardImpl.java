package org.fufeng.action.lambda.p2;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PriceDashboardImpl implements PriceDashboard {
    private Map<String, Price> stockPrices = new HashMap<>();

    public void init() {
        stockPrices.put("APPL", Price.builder()
                .currPrice(new BigDecimal("130.06"))
                .build());
    }

    @Override
    public Price getPrice(String symbol) {
        PriceDashboard pb = new PriceDashboard() {
            @Override
            public Price getPrice(String symbol) {
                log.info("log this action");
                return stockPrices.get(symbol);
            }
        };
        return pb.getPrice(symbol);
    }
}
