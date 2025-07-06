package org.fufeng.reactive.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockSubscription {
    public String email;
    public String symbol;
}
