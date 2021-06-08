package org.fufeng.project.busy.waiter.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.fufeng.project.busy.waiter.model.OrderState;

@Getter
@Setter
@ToString
public class OrderStateRequest {
    private OrderState state;
}
