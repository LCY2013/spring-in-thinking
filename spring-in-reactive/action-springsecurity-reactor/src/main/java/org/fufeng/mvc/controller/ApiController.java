package org.fufeng.mvc.controller;

import org.fufeng.mvc.model.Price;
import org.fufeng.mvc.model.Stock;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("/api")
public class ApiController {
    @PostMapping("/price")
    public Map<Stock, Price> getPrice() {
        Map<Stock, Price> result = new HashMap<>();
        result.put(Stock.builder().symbol("APPL").build(),
                Price.builder().coefficient(10100).exponent(-2).currency("USD").build());
        return result;
    }
}
