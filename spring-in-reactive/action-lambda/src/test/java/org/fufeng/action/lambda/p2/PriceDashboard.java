package org.fufeng.action.lambda.p2;

@FunctionalInterface
public interface PriceDashboard {
    Price getPrice(String symbol);
}
