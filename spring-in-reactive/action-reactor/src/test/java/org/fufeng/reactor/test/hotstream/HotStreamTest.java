package org.fufeng.reactor.test.hotstream;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;


public class HotStreamTest {
    @Test
    public void simpleHotStreamCreation() {
        Sinks.Many<Integer> hotSource = Sinks.unsafe().many().multicast().directBestEffort();
        Flux<Integer> hotFlux = hotSource.asFlux();
        hotFlux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: " + d));

        hotSource.emitNext(1, FAIL_FAST);
        hotSource.tryEmitNext(2).orThrow();

        hotFlux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: " + d));

        hotSource.emitNext(3, FAIL_FAST);
        hotSource.emitNext(4, FAIL_FAST);
        hotSource.emitComplete(FAIL_FAST);

    }

    @Test
    public void connectableFlux() throws InterruptedException {
        Flux<Integer> source = Flux.range(1, 4);
        ConnectableFlux<Integer> connectableFlux = source.publish();
        connectableFlux.subscribe(d -> System.out.println("Subscriber 1 gets " + d));
        connectableFlux.subscribe(d -> System.out.println("Subscriber 2 gets " + d));

        System.out.println("Finish subscribe action");
        Thread.sleep(1000L);
        System.out.println("Connect to Flux now");
        connectableFlux.connect();
    }

    @Test
    public void autoConnectConnectableFlux() throws InterruptedException {
        Flux<Integer> source = Flux.range(1, 4);
        Flux<Integer> autoConnect = source.publish().autoConnect(2);
        autoConnect.subscribe(d -> System.out.println("Subscriber 1 gets " + d));
        System.out.println("Finish subscriber 1 action");
        Thread.sleep(1000L);
        System.out.println("Start subscriber 2 action");
        autoConnect.subscribe(d -> System.out.println("Subscriber 2 gets " + d));
    }
}
