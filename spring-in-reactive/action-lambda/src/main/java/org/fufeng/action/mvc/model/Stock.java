package org.fufeng.action.mvc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stock {
    public Stock() {
    }

    public Stock(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    private String symbol;
    private String name;
}
