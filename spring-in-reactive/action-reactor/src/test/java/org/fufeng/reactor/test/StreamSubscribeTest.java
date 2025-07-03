package org.fufeng.reactor.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Slf4j
public class StreamSubscribeTest {
    @Test
    public void subscribeMethod() {
        Flux<String> stockSeq1 = Flux.just("APPL", "AMZN", "TSLA");
        stockSeq1.subscribe();  //subscribe with no arguments

        stockSeq1.subscribe(i -> log.info(i)); //subscriber that will print the values
//        stockSeq1.subscribe(i -> log.info(i));
    }

    @Test
    public void subscribeWithErrorConsumer() {
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("i>3");
                });
        ints.subscribe(
                i -> log.info(String.valueOf(i)),
                err -> log.error("error: {}", err.getMessage())
        );
    }

    @Test
    public void subscribeWithErrorConsumerAndCompleteConsumer() {
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("i>3");
                });
        ints.subscribe(
                i -> log.info(String.valueOf(i)),
                err -> log.error("error: {}", err.getMessage()),
                () -> log.info("Subscription completed")
        ); //errorConsumer and complete consumer are mutually exclusive
    }

    @Test
    public void subscribeWithSubscription() {
        Flux<Integer> ints = Flux.range(1, 4);
        Consumer<? super Subscription> subscriptionConsumer1 = null;
        Consumer<? super Subscription> subscriptionConsumer2 = sub -> sub.request(3);
        Consumer<? super Subscription> subscriptionConsumer3 = sub -> sub.request(5);
        Consumer<? super Subscription> subscriptionConsumer4 = sub -> sub.cancel();

        ints.subscribe(i -> log.info(String.valueOf(i)),
                null,
                () -> log.info("Subscription completed"),
                subscriptionConsumer4
        );
    }

    @Test
    public void subscribeWithBaseSubscriber() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(new SampleSubscriber<>());
    }

    public class SampleSubscriber<T> extends BaseSubscriber<T> {
        @Override
        public void hookOnSubscribe(Subscription subscription) {
            log.info("Subscribed");
            request(1);
        }

        @Override
        public void hookOnNext(T value) {
            log.info(value.toString());
            request(1);
        }
    }
}
