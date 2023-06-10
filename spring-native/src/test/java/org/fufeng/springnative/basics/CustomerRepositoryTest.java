package org.fufeng.springnative.basics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// <1>
@SpringBootTest(properties = "spring.main.web-application-type=none")
class CustomerRepositoryTest {

    private final CustomerRepository repository;

    CustomerRepositoryTest(@Autowired CustomerRepository repository) {
        this.repository = repository;
    }

    @Test
    void persist() {
        var saved = repository.save(new Customer(null, "Name"));
        Assertions.assertNotNull(saved.id());
        Assertions.assertNotNull(saved);
    }

}