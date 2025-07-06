package org.fufeng.webtestclient;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

public class Demo3 {

    @Test
    public void bindToServer() {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8088").build();
        client.post().uri("/api/price").exchange()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    public void bindToExternalServer() {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl("https://quotes.rest").build();
        client.get().uri(
                uriBuilder -> uriBuilder.path("/qod")
                        .queryParam("language", "en")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody()
                .consumeWith(System.out::println);
    }
}
