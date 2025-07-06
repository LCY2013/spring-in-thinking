package org.fufeng;

import org.fufeng.reactive.controller.SubscriptionController;
import org.fufeng.reactive.model.StockSubscription;
import org.fufeng.reactive.service.RSubscriptionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;

@WebFluxTest(value = SubscriptionController.class)
public class SubscriptionControllerTest {
    @MockBean
    private RSubscriptionService rSubscriptionService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @WithMockUser(username = "tester@qq.com", roles = {"ADMIN"})
    public void shouldReturnView() {
        Mockito.when(rSubscriptionService.findByEmail(anyString()))
                .thenReturn(buildSubscriptionList());
        this.webTestClient
                .get()
                .uri("/subscriptions")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(body -> System.out.println(new String(body.getResponseBody())));
    }

    private Flux<StockSubscription> buildSubscriptionList() {
        return Flux.fromIterable(
                Arrays.asList(
                        StockSubscription.builder().email("tester@qq.com")
                                .symbol("APPL").build()
                )
        );
    }
}
