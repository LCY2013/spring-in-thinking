package org.fufeng.reactor.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class StreamParallelFluxTest {
    @Test
    public void parallelFlux() {
        Flux<Integer> flux = Flux.range(1, 20);
        ParallelFlux<Integer> paralleled = flux.parallel().runOn(Schedulers.parallel());
//        paralleled.subscribe(i -> log.info("print {} on {}", i, Thread.currentThread().getName()));
        paralleled.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.info("print {} on {}", integer, Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void afterParallelFlux() {
        Flux<Integer> flux = Flux.range(1, 20);
        ParallelFlux<Integer> paralleled = flux.parallel();
        Flux<Integer> normalized = paralleled
                .runOn(Schedulers.parallel())
                .map(i -> i + 1)
                .sequential();
        normalized.subscribe(i -> log.info("print {} on {}", i, Thread.currentThread().getName()));
    }
}
