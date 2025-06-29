package org.fufeng.servlet.mvc.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Stock {
    private String symbol;
    private String name;
}
