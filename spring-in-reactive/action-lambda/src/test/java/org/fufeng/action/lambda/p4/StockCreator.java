package org.fufeng.action.lambda.p4;

import org.fufeng.action.mvc.model.Stock;

@FunctionalInterface
public interface StockCreator {
    Stock getStock();
}
