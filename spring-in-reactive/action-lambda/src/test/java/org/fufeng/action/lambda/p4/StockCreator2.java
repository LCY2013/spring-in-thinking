package org.fufeng.action.lambda.p4;

import org.fufeng.action.mvc.model.Stock;

@FunctionalInterface
public interface StockCreator2 {
    Stock getStock(String symbol, String name);
}
