package org.fufeng.reactor.test.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class StreamScheulderNewTest {
    //Two ways of changing execution context
    //  1. subscribeOn(scheduler)
    //  2. publishOn(scheduler)
    @Test
    public void simpleSubscribeOn() throws InterruptedException {
        Scheduler s = Schedulers.newBoundedElastic(10, 10, "subscribeOn-demo-elastic");
        Scheduler s2 = Schedulers.newBoundedElastic(10, 10, "subscribeOn222-demo-elastic");
        Flux<Integer> flux = Flux.range(1, 4)
                .filter(i -> {
                    log.info("filter in thread {}", Thread.currentThread().getName());
                    return i % 2 == 0;
                })
                .subscribeOn(s)
                .map(i -> {
                    log.info("map in thread {}", Thread.currentThread().getName());
                    return i + 2;
                });
        Thread t = new Thread(() -> {
            log.info("start current thread");
            flux.subscribeOn(s2).subscribe(i -> log.info(String.valueOf(i)));
            log.info("end current thread");
        });
        t.start();
        t.join();
    }

    @Test
    public void simplePublishOn() throws InterruptedException {
        Scheduler s = Schedulers.newBoundedElastic(10, 10,"publishOn-demo-elastic");
        Scheduler s2 = Schedulers.newBoundedElastic(10, 10,"p2-demo-elastic");
        Flux<Integer> flux = Flux.range(1, 4)
                .publishOn(s2)
                .filter(i -> {
                    log.info("filter in thread {}", Thread.currentThread().getName());
                    return i % 2 == 0;
                })
                .publishOn(s)
                .map(i -> {
                    log.info("map in thread {}", Thread.currentThread().getName());
                    return i + 2;
                });
        Thread t = new Thread(() -> {
            log.info("start current thread");
            flux.subscribe(i -> log.info(String.valueOf(i)));
            log.info("end current thread");
        });
        t.start();
        t.join();
    }

    @Test
    public void trickyCombination() throws InterruptedException {
        Scheduler p1 = Schedulers.newBoundedElastic(10, 10,"publishOn-demo-elastic");
        Scheduler s1 = Schedulers.newBoundedElastic(10, 10,"subscribeOn-demo-elastic");
        Flux<Integer> flux = Flux.range(1, 4)
                .subscribeOn(s1)
                .filter(i -> {
                    log.info("filter in thread {}", Thread.currentThread().getName());
                    return i % 2 == 0;
                })
                .publishOn(p1)
                .map(i -> {
                    log.info("map in thread {}", Thread.currentThread().getName());
                    return i + 2;
                });
        Thread t = new Thread(() -> {
            flux.subscribe(i -> log.info(String.valueOf(i)));
        });
        t.start();
        t.join();
    }
}
