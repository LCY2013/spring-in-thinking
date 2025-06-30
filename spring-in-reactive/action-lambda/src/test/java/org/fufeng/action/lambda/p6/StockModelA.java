package org.fufeng.action.lambda.p6;

import lombok.Data;

@Data
public class StockModelA {
    private String symbol;
    private String name;

    public StockModelA(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }
}
