package org.fufeng.reactive.rsocket.client;

import org.fufeng.reactive.dto.StockPrice;
import org.fufeng.reactive.model.Stock;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;

public class RSocketClientApplication {
    public static void main(String[] args) throws InterruptedException {
        RSocketStrategies strategies = RSocketStrategies.builder()
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .build();

        RSocketRequester.Builder builder = RSocketRequester.builder();
        RSocketRequester requester = builder
                .rsocketConnector(rSocketConnector ->
                        rSocketConnector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(5)))
                                .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                )
                .rsocketStrategies(strategies)
//                .tcp("localhost", 8099);
                .websocket(URI.create("ws://localhost:8120/rsocket"));

        Mono<StockPrice> result = requester
                .route("currentStockPrice")
                .data(new Stock("APPL", "Apple Inc."))
                .retrieveMono(StockPrice.class);
        System.out.println(result.block());
    }
}