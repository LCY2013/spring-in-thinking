package org.fufeng.reactive.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stock {
    private String symbol;
    private String name;
}
