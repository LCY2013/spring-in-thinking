package org.fufeng.reactor.test.scheduler;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class StreamSchedulerTest {
    @Test
    public void noThreadDefined() {
        Mono<String> mono = Mono.just("foo");
        mono
                .map(str -> str + " with no thread defined ")
                .subscribe(str -> System.out.println(str + Thread.currentThread().getName()));
    }

    @Test
    public void runInNewThread() throws InterruptedException {
        Thread t = new Thread(() -> {
            Mono<String> mono = Mono.just("foo");
            mono
                    .map(str -> str + " with no thread defined ")
                    .subscribe(str -> System.out.println(str + Thread.currentThread().getName()));
        });
        t.start();
        t.join();
    }

    @Test
    public void schedulerImmediate() {
        Mono<String> mono = Mono.just("foo");
        mono
                .map(str -> str + " with scheduler defined ")
                .subscribeOn(Schedulers.immediate())
                .subscribe(str -> System.out.println(str + Thread.currentThread().getName()));
    }

    @Test
    public void schedulerSingle() {
        Mono<String> mono = Mono.just("foo");
        mono
                .map(str -> str + " with scheduler defined ")
                .subscribeOn(Schedulers.single())
                .subscribe(str -> System.out.println(str + Thread.currentThread().getName()));
    }

    @Test
    public void schedulerElastic() {
        Mono<String> mono = Mono.just("foo");
        mono
                .map(str -> str + " with scheduler defined ")
//                .subscribeOn(Schedulers.elastic())
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(str -> System.out.println(str + Thread.currentThread().getName()));

        //if I need to wrap a blocking call
        Mono<String> fromBlockingCall = Mono.fromCallable(() -> {
            /**a blocking call to downstream**/
            return "result";
        });
        Mono<String> wrappedBlockingCall = fromBlockingCall.subscribeOn(Schedulers.boundedElastic());
    }

    @Test
    public void schedulerParallel() {
        Mono<String> mono = Mono.just("foo");
        mono
                .map(str -> str + " with scheduler defined ")
                .subscribeOn(Schedulers.parallel())
                .subscribe(str -> System.out.println(str + Thread.currentThread().getName()));
    }

}
