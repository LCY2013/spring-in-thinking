package org.fufeng.servlet.mvc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockSubscription {
    private String email;
    private String symbol;
}
