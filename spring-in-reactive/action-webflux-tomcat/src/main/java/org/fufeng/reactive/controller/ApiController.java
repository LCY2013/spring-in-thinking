package org.fufeng.reactive.controller;


import org.fufeng.reactive.dto.StockPrice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/prices")
    public Flux<StockPrice> getPrices() {
        return Flux.create(sink -> {
            sink.next(new StockPrice("APPL", "130"));
            sink.next(new StockPrice("AMZN", "2300"));
            sink.complete();
        });
    }

    @GetMapping(path = "/price/{symbol}")
    public Mono<StockPrice> getPrice(@PathVariable("symbol") String symbol) {
        return Mono.just(
                new StockPrice(symbol, "100")
        );
    }

////    @GetMapping(path = "/price2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
////    public Flux<StockPrice> getPrice2() {
////        return Flux.error(new IllegalArgumentException("empty args"));
////    }
////
////    @ExceptionHandler({IllegalArgumentException.class})
////    public String exception(IllegalArgumentException e) {
////        return "oops, something went wrong";
////    }
}
