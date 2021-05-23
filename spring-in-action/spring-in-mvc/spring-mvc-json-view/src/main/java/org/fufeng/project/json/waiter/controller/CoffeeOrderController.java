package org.fufeng.project.json.waiter.controller;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.project.json.waiter.controller.request.NewOrderRequest;
import org.fufeng.project.json.waiter.model.Coffee;
import org.fufeng.project.json.waiter.model.CoffeeOrder;
import org.fufeng.project.json.waiter.service.CoffeeOrderService;
import org.fufeng.project.json.waiter.service.CoffeeService;
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

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {
    @Autowired
    private CoffeeOrderService orderService;
    @Autowired
    private CoffeeService coffeeService;

    /**
     * content-type: produces
     * <p>
     * Accept(SpringMvc 根据客户端需要的内容类型[MediaType]生成不同的数据类型): consumes
     *
     * @param id 订单id
     * @return {@link CoffeeOrder}
     */
    @GetMapping(value = "/{id}")
    public CoffeeOrder getOrder(@PathVariable("id") Long id) {
        return orderService.get(id);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder) {
        log.info("Receive new Order {}", newOrder);
        Coffee[] coffeeList = coffeeService.getCoffeeByName(newOrder.getItems())
                .toArray(new Coffee[]{});
        return orderService.createOrder(newOrder.getCustomer(), coffeeList);
    }
}
