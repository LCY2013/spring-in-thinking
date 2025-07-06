package org.fufeng.client;

import org.fufeng.mvc.model.StockPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class WebClientCaller {
    public static void main(String[] args) throws InterruptedException {
        String baseUrl = "http://localhost:8088/";
        String apiPricePath = "/api/v2/price/{symbol}";
        WebClient client_ = WebClient.create();
//        WebClient client = WebClient.create(baseUrl);
        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE).build();

//        client.method(HttpMethod.GET);
        WebClient.RequestHeadersSpec<?> spec = client
                .get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path(apiPricePath)
                                .build("APPL")
                )
                .header("x-b3-traceid", "some id");

        Mono<StockPrice> response = spec.exchangeToMono(clientResponse -> {
            if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                return clientResponse.bodyToMono(StockPrice.class);
            } else {
                return clientResponse.createException().flatMap(Mono::error);
            }
        });
//        spec
//                .retrieve()
//                .onStatus(
//                        HttpStatus::is4xxClientError,
//                        error -> Mono.error(new RuntimeException("client error"))
//                )
//                .onStatus(
//                        HttpStatus::is5xxServerError,
//                        error -> Mono.error(new RuntimeException("server error"))
//                )
//                .bodyToMono(StockPrice.class)
//                .doOnError(error -> log.error("error {}", error.getMessage()));
        response.subscribe(stockPrice -> System.out.println("this is the result " + stockPrice));
        Thread.sleep(10000L);
    }
}
