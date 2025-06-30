package org.fufeng.action.lambda.p1;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DemoPart1Test {
    private Map<String, Price> stockPrices = new HashMap<>();
    private int accessCount = 0;

    public void init() {
        stockPrices.put("APPL", Price.builder()
                .currPrice(new BigDecimal("130.06"))
                .build());
    }

    private class PriceDashboardNewImpl implements PriceDashboard {
        @Override
        public Price getPrice(String symbol) {
            log.info("log this inner class action");
            return stockPrices.get(symbol);
//            log.info("about to reference by this");
//            return DemoPartOneTest.this.stockPrices.get(symbol);
        }
    }

    public static void main(String[] args) {
        DemoPart1Test test = new DemoPart1Test();
        test.init();
        PriceDashboard priceDashboard = test.new PriceDashboardNewImpl();
        log.info(priceDashboard.getPrice("APPL").toString());
    }
}
