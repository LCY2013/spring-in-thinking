package org.fufeng.project.lazy.customer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class OrderStateRequest {
    private OrderState state;
}
