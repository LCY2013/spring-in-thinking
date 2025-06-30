package org.fufeng.action.lambda.p6;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockModelB {
    private String symbol;
    private String name;
    private String description;
    private BigDecimal currentPrice;
    private BigDecimal prevClosePrice;

    public StockModelB(String symbol, String name, String description, BigDecimal currentPrice, BigDecimal prevClosePrice) {
        this.symbol = symbol;
        this.name = name;
        this.description = description;
        this.currentPrice = currentPrice;
        this.prevClosePrice = prevClosePrice;
    }
}
