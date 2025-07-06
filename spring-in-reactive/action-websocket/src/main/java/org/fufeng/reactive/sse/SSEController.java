package org.fufeng.reactive.sse;

import org.fufeng.reactive.service.PriceQueryEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/sse")
@Slf4j
public class SSEController {
    @Autowired
    private PriceQueryEngine priceQueryEngine;

    @GetMapping(path = "/price/{symbol}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux(@PathVariable("symbol") String symbol) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> priceQueryEngine.getPriceForSymbol(symbol));
    }

    @GetMapping(path = "/priceEvent/{symbol}")
    public Flux<ServerSentEvent<String>> streamEvent(@PathVariable("symbol") String symbol) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> ServerSentEvent.<String>builder()
                        .data(priceQueryEngine.getPriceForSymbol(symbol))
                        .id(String.valueOf(seq))
                        .event("price_updated")
                        .build());
    }


}
