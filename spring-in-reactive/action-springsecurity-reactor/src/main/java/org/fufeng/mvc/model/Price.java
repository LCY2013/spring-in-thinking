package org.fufeng.mvc.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Price {
    private Integer coefficient;
    private Integer exponent;
    private String currency;
}
