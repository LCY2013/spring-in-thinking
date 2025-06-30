package orf.fufeng.action.mvc.controller;

import orf.fufeng.action.mvc.model.Price;
import orf.fufeng.action.mvc.model.Stock;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    @PostMapping("/price")
    public Map<Stock, Price> getPrice() {
        Map<Stock, Price> result = new HashMap<>();
        result.put(Stock.builder().symbol("APPL").build(),
                Price.builder().coefficient(10100).exponent(-2).currency("USD").build());
        return result;
    }
}
