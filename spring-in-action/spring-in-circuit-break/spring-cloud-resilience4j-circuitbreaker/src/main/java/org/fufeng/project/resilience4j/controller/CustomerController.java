package org.fufeng.project.resilience4j.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.fufeng.project.resilience4j.integration.CoffeeOrderService;
import org.fufeng.project.resilience4j.integration.CoffeeService;
import org.fufeng.project.resilience4j.model.Coffee;
import org.fufeng.project.resilience4j.model.CoffeeOrder;
import org.fufeng.project.resilience4j.model.NewOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeOrderService coffeeOrderService;
    private CircuitBreaker circuitBreaker;

    public CustomerController(CircuitBreakerRegistry registry) {
        circuitBreaker = registry.circuitBreaker("menu");
    }

    @GetMapping("/menu")
    public List<Coffee> readMenu() {
        return Try.ofSupplier(
                CircuitBreaker.decorateSupplier(circuitBreaker,
                        () -> coffeeService.getAll()))
                .recover(Exception.class, Collections.emptyList())
                .get();
    }

    @PostMapping("/order")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "order")
    public CoffeeOrder createOrder() {
        NewOrderRequest orderRequest = NewOrderRequest.builder()
                .customer("Li Lei")
                .items(Arrays.asList("capuccino"))
                .build();
        CoffeeOrder order = coffeeOrderService.create(orderRequest);
        log.info("Order ID: {}", order != null ? order.getId() : "-");
        return order;
    }
}
