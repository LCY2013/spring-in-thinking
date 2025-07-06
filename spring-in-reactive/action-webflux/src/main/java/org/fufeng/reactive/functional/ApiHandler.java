package org.fufeng.reactive.functional;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.fufeng.reactive.dto.StockPrice;

public class ApiHandler {
    public Mono<ServerResponse> getPrices(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Flux.create(sink -> {
                            sink.next(new StockPrice("APPL", "130"));
                            sink.next(new StockPrice("AMZN", "2300"));
                            sink.complete();
                        }), StockPrice.class);
    }

    public Mono<ServerResponse> getPrice(ServerRequest request) {
        String symbol = request.pathVariable("symbol");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Mono.just(
                                new StockPrice(symbol, "100")
                        ), StockPrice.class);
    }
}
