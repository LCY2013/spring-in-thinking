package org.fufeng.project.rabbitmq.waiter.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.fufeng.project.rabbitmq.waiter.model.OrderState;

@Getter
@Setter
@ToString
public class OrderStateRequest {
    private OrderState state;
}
