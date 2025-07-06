package org.fufeng.reactive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockPrice {
    private String stock;
    private String price;
}
