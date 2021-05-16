package org.fufeng.project.orm.jpa;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.project.orm.jpa.model.Coffee;
import org.fufeng.project.orm.jpa.model.CoffeeOrder;
import org.fufeng.project.orm.jpa.repository.CoffeeOrderRepository;
import org.fufeng.project.orm.jpa.repository.CoffeeRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class OrmJpaApplication implements ApplicationRunner {
	@Resource
	private CoffeeRepository coffeeRepository;
	@Resource
	private CoffeeOrderRepository orderRepository;

	public static void main(String[] args) {
		SpringApplication.run(OrmJpaApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		initOrders();
	}

	private void initOrders() {
		Coffee espresso = Coffee.builder().name("espresso")
				.price(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.build();
		coffeeRepository.save(espresso);
		log.info("Coffee: {}", espresso);
		Coffee latte = Coffee.builder().name("latte")
				.price(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.build();
		coffeeRepository.save(latte);
		log.info("Coffee: {}", latte);

		CoffeeOrder order = CoffeeOrder.builder()
				.customer("Li Lei")
				.items(Collections.singletonList(espresso))
				.state(0)
				.build();
		orderRepository.save(order);
		log.info("Order: {}", order);

		order = CoffeeOrder.builder()
				.customer("Li Lei")
				.items(Arrays.asList(espresso, latte))
				.state(0)
				.build();
		orderRepository.save(order);
		log.info("Order: {}", order);
	}
}

