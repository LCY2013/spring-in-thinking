package org.fufeng.action.lambda.p1;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DemoPart3Test {
    private Map<String, Price> stockPrices = new HashMap<>();

    public void init() {
        stockPrices.put("APPL", Price.builder()
                .currPrice(new BigDecimal("130.06"))
                .build());
    }

    public PriceDashboard anonPriceBoard() {
        return symbol -> {
            log.info("log this in-method anonymous action");
            return stockPrices.get(symbol);
        };
    }

    public static void main(String[] args) {
        DemoPart3Test test = new DemoPart3Test();
        test.init();
        PriceDashboard priceDashboard = test.anonPriceBoard();
        log.info(priceDashboard.getPrice("APPL").toString());
    }
}
