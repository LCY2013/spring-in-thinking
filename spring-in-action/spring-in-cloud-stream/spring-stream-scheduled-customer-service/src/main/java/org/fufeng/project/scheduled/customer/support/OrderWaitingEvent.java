package org.fufeng.project.scheduled.customer.support;

import lombok.Data;
import org.fufeng.project.scheduled.customer.model.CoffeeOrder;
import org.springframework.context.ApplicationEvent;

public class OrderWaitingEvent extends ApplicationEvent {
    private CoffeeOrder order;

    public OrderWaitingEvent(CoffeeOrder order) {
        super(order);
        this.order = order;
    }

    public CoffeeOrder getOrder() {
        return order;
    }

    public void setOrder(CoffeeOrder order) {
        this.order = order;
    }
}
