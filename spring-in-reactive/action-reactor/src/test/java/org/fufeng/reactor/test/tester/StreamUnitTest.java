package org.fufeng.reactor.test.tester;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@Slf4j
public class StreamUnitTest {
    @Test
    public void simpleStepVerifier() {
        Flux<Integer> source = Flux.range(1, 7)
                .filter(i -> i % 2 == 1)
                .map(i -> i * 10);
        Flux<Integer> fluxException = source.concatWith(Mono.error(new RuntimeException("oops")));
        StepVerifier.create(fluxException)
                .expectNext(10)
                .expectNextMatches(i -> i % 10 == 0)
                .expectNext(50, 70)
                .expectErrorMatches(e -> e.getMessage().equals("oops"))
                .verify();
    }

    @Test
    public void stepVerifierWithConsumption() {
        Flux<Integer> source = Flux.range(1, 7)
                .filter(i -> i % 2 == 1)
                .map(i -> i * 10);

        StepVerifier
                .create(source)
                .expectNext(10)
                .thenConsumeWhile(i -> i < 100)
//                .expectNext(50, 70)
                .expectComplete()
                .verifyThenAssertThat()
                .hasDiscarded(2, 4, 6)
                .tookLessThan(Duration.ofSeconds(3));
    }


    @Test
    public void testTimeBasedSource() {
        Flux<Long> flux = Flux.interval(Duration.ofDays(1)).take(2);
        StepVerifier.withVirtualTime(() ->Flux.interval(Duration.ofDays(1)).take(2))
                .expectSubscription()
                .expectNoEvent(Duration.ofDays(1))
                .expectNext(0L)
                .thenAwait(Duration.ofDays(1))
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    public void testThenAwait() {
        StepVerifier
                .withVirtualTime(() -> Flux.interval(Duration.ofDays(1)).take(2))
                .thenAwait(Duration.ofDays(3))
                .expectNext(0L)
                .expectNext(1L)
                .expectComplete();

    }
}

