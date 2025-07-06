package org.fufeng.reactive.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
public class SSEWebClient {
    public static void main(String[] args) throws InterruptedException {
        WebClient client = WebClient.create("http://localhost:8108");

        ParameterizedTypeReference<ServerSentEvent<String>> type =
                new ParameterizedTypeReference<ServerSentEvent<String>>() {
                };
        Flux<ServerSentEvent<String>> eventStream = client.get()
                .uri("/sse/priceEvent/APPL")
                .retrieve()
                .bodyToFlux(type);

        eventStream.subscribe(
                content -> log.info("event: {}, id {}, data {}", content.event(), content.id(), content.data()),
                error -> log.error("Error receiving SSE: {}", error),
                () -> log.info("Completed")
        );

        Thread.sleep(100000);
    }
}
