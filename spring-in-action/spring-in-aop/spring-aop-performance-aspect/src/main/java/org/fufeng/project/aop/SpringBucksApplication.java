package org.fufeng.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.project.aop.model.Coffee;
import org.fufeng.project.aop.model.CoffeeOrder;
import org.fufeng.project.aop.model.OrderState;
import org.fufeng.project.aop.repository.CoffeeRepository;
import org.fufeng.project.aop.service.CoffeeOrderService;
import org.fufeng.project.aop.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy
public class SpringBucksApplication implements ApplicationRunner {
	@Resource
	private CoffeeRepository coffeeRepository;
	@Resource
	private CoffeeService coffeeService;
	@Resource
	private CoffeeOrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("All Coffee: {}", coffeeRepository.findAll());

		Optional<Coffee> latte = coffeeService.findOneCoffee("Latte");
		if (latte.isPresent()) {
			CoffeeOrder order = orderService.createOrder("Li Lei", latte.get());
			log.info("Update INIT to PAID: {}", orderService.updateState(order, OrderState.PAID));
			log.info("Update PAID to INIT: {}", orderService.updateState(order, OrderState.INIT));
		}
	}
}

