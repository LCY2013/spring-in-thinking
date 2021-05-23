package org.fufeng.project.waiter.controller;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.project.waiter.controller.request.NewOrderRequest;
import org.fufeng.project.waiter.model.Coffee;
import org.fufeng.project.waiter.model.CoffeeOrder;
import org.fufeng.project.waiter.service.CoffeeOrderService;
import org.fufeng.project.waiter.service.CoffeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {
    @Resource
    private CoffeeOrderService orderService;
    @Resource
    private CoffeeService coffeeService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder) {
        log.info("Receive new Order {}", newOrder);
        Coffee[] coffeeList = coffeeService.getCoffeeByName(newOrder.getItems())
                .toArray(new Coffee[0]);
        return orderService.createOrder(newOrder.getCustomer(), coffeeList);
    }
}
