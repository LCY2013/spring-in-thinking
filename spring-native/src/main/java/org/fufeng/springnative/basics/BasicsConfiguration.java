package org.fufeng.springnative.basics;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

@Configuration
public class BasicsConfiguration {

    @Bean
    ApplicationListener<ApplicationReadyEvent> basicsApplicationListener(CustomerRepository repository) {
        return event -> repository.saveAll(
                        Stream.of("a", "b", "c").
                                map(name -> new Customer(null, name)).toList()).
                forEach(System.out::println);
    }

}

record Customer(@Id Integer id, String name) {
}

interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
