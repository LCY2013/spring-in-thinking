package org.fufeng.project;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.project.model.Coffee;
import org.fufeng.project.model.CoffeeOrder;
import org.fufeng.project.model.OrderState;
import org.fufeng.project.service.CoffeeService;
import org.fufeng.project.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

//@Slf4j
@Component
public class SpringbucksRunner implements ApplicationRunner {
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private OrderService orderService;

    private final static Logger log = LoggerFactory.getLogger(SpringbucksRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        coffeeService.initCache()
                .then(
                        coffeeService.findOneCoffee("mocha")
                                .flatMap(c -> {
                                    CoffeeOrder order = createOrder("Li Lei", c);
                                    return orderService.create(order);
                                })
                                .doOnError(t -> log.error("error", t)))
                .subscribe(o -> log.info("Create Order: {}", o));
        log.info("After Subscribe");
        Thread.sleep(5000);
    }

    private CoffeeOrder createOrder(String customer, Coffee... coffee) {
        return CoffeeOrder.builder()
                .customer(customer)
                .items(Arrays.asList(coffee))
                .state(OrderState.INIT)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
    }
}
