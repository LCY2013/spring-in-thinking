package org.fufeng.project.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.project.webflux.controller.request.NewOrderRequest;
import org.fufeng.project.webflux.model.CoffeeOrder;
import org.fufeng.project.webflux.service.CoffeeService;
import org.fufeng.project.webflux.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/{id}")
    public Mono<CoffeeOrder> getOrder(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CoffeeOrder> create(@RequestBody NewOrderRequest newOrder) {
        log.info("Receive new Order {}", newOrder);
        return orderService.create(newOrder.getCustomer(), newOrder.getItems())
                .flatMap(id -> orderService.getById(id));
    }
}
