package org.fufeng.servlet.mvc.controller;

import jakarta.validation.*;
import org.fufeng.servlet.mvc.model.Stock;
import org.fufeng.servlet.mvc.model.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/validate/user/v1")
    public Object validateUserV1Get() {
        return "cool";
    }

    @PostMapping("/validate/user/v1")
    public Object validateUserV1(@Valid @RequestBody User user) {
        return "cool";
    }

    @PostMapping("/validate/user/v2")
    public Object validateUserV2(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        } else {
            return "cool";
        }
    }

    @PostMapping("/price")
    public Map<Stock, String> getPrice() {
        Map<Stock, String> result = new HashMap<>();
        result.put(Stock.builder().symbol("APPL").build(), "USD101.00");
        result.put(Stock.builder().symbol("AMZN").build(), "USD3298.75");
        result.put(Stock.builder().symbol("TSLA").build(), "USD701.98");
        return result;
    }
}
