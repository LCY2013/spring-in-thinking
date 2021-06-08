package org.fufeng.project.scheduled.customer.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.project.scheduled.customer.integration.CoffeeOrderService;
import org.fufeng.project.scheduled.customer.model.CoffeeOrder;
import org.fufeng.project.scheduled.customer.model.OrderState;
import org.fufeng.project.scheduled.customer.model.OrderStateRequest;
import org.fufeng.project.scheduled.customer.support.OrderWaitingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class CoffeeOrderScheduler {
    @Autowired
    private CoffeeOrderService coffeeOrderService;
    private Map<Long, CoffeeOrder> orderMap = new ConcurrentHashMap<>();

    @EventListener
    public void acceptOrder(OrderWaitingEvent event) {
        orderMap.put(event.getOrder().getId(), event.getOrder());
    }

    @Scheduled(fixedRate = 1000)
    public void waitForCoffee() {
        if (orderMap.isEmpty()) {
            return;
        }
        log.info("I'm waiting for my coffee.");
        orderMap.values().stream()
                .map(o -> coffeeOrderService.getOrder(o.getId()))
                .filter(o -> OrderState.BREWED == o.getState())
                .forEach(o -> {
                    log.info("Order [{}] is READY, I'll take it.", o);
                    coffeeOrderService.updateState(o.getId(),
                            OrderStateRequest.builder()
                                    .state(OrderState.TAKEN).build());
                    orderMap.remove(o.getId());
                });
    }
}
