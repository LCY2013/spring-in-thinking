package org.fufeng.mvc.controller;

import org.fufeng.mvc.model.Price;
import org.fufeng.mvc.model.Stock;
import org.fufeng.mvc.model.StockPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {
    @GetMapping("/prices")
    public Map<String, String> getPrices() {
        Map<String, String> result = new HashMap<>();
        result.put("APPL", "100");
        result.put("AMZN", "2500");
        return result;
    }

    @GetMapping("/price/{symbol}")
    public Map<String, String> getPrice(@PathVariable("symbol") String symbol) {
        Map<String, String> result = new HashMap<>();
        result.put(symbol, "100");
        return result;
    }

    @GetMapping("/v2/price/{symbol}")
    public StockPrice getPriceV2(@PathVariable("symbol") String symbol) {
        StockPrice stockPrice = StockPrice.builder()
                .stock(Stock.builder().symbol("APPLE").name("Apple Inc.").build())
                .price(Price.builder().coefficient(13079).exponent(-2).currency("USD").build())
                .build();
        return stockPrice;
    }

    @PostMapping("/v2/price/{symbol}")
    public String setPriceV2(@PathVariable("symbol") String symbol, @RequestBody StockPrice stockPrice) {
        log.info("received from reactive client " + stockPrice);
        return "done";
    }
}
