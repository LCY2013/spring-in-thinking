package org.fufeng.reactive.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fufeng.reactive.dto.StockPrice;
import org.fufeng.reactive.model.StockSubscription;
import org.fufeng.reactive.service.PriceQueryEngine;
import org.fufeng.reactive.service.RSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {

    @Autowired
    public PriceQueryEngine priceQueryEngine;

    @Autowired
    public RSubscriptionService rSubscriptionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(email -> rSubscriptionService.findByEmail(email))
                .flatMap(subscription -> registerSymbolForQuote(subscription, session))
        );
    }

    public Flux<WebSocketMessage> registerSymbolForQuote(StockSubscription subscription, WebSocketSession session) {
        return Flux.interval(Duration.ofSeconds(2))
                .map(seq ->
                        new StockPrice(subscription.getSymbol(),
                                priceQueryEngine.getPriceForSymbol(subscription.getSymbol()))
                )
                .map(stockPrice -> session.textMessage(constructKvPair(stockPrice)));
    }

    private String constructKvPair(StockPrice stockPrice) {
        return stockPrice.getStock() + ":" + stockPrice.getPrice();
    }

//    @Override
//    public Mono<Void> handle(WebSocketSession webSocketSession) {
//        return webSocketSession
//                .send(getPriceQueryFlux("APPL").map(webSocketSession::textMessage))
//                .and(webSocketSession.receive()
//                        .map(WebSocketMessage::getPayloadAsText)
//                        .log());
//    }

//    public Flux<String> getPriceQueryFlux(String symbol) {
//        return Flux.interval(Duration.ofSeconds(1))
//                .map(seq ->
//                        String.join(" ",
//                                String.valueOf(seq),
//                                symbol,
//                                priceQueryEngine.getPriceForSymbol(symbol)
//                        )
//                );
//    }
}