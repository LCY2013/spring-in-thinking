package org.fufeng.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private Integer coefficient;
    private Integer exponent;
    private String currency;
}
