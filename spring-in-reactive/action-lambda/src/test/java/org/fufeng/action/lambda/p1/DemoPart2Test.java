package org.fufeng.action.lambda.p1;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DemoPart2Test {
    private Map<String, Price> stockPrices = new HashMap<>();
    private int accessCount = 0;

    public void init() {
        stockPrices.put("APPL", Price.builder()
                .currPrice(new BigDecimal("130.06"))
                .build());
    }

    public PriceDashboard priceBoard() {
         class PriceDashboardInMethodImpl implements PriceDashboard {
            @Override
            public Price getPrice(String symbol) {
                log.info("log this in-method action");
                return stockPrices.get(symbol);
            }
        }
        return new PriceDashboardInMethodImpl();
    }

    public static void main(String[] args) {
        DemoPart2Test test = new DemoPart2Test();
        test.init();
        PriceDashboard priceDashboard = test.priceBoard();
        log.info(priceDashboard.getPrice("APPL").toString());
    }
}
