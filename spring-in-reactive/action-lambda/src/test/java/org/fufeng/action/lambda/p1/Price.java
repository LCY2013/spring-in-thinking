package org.fufeng.action.lambda.p1;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Price {
    private BigDecimal currPrice;
    private BigDecimal prevPrice;
}
